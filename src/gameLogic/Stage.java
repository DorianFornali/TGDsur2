package gameLogic;

import audio.AudioPlayer;
import gameLogic.entity.*;
import gameLogic.spawn.SpawnObject;
import gameLogic.spawn.SpawnObjectStack;
import gameView.ViewController;
import observerPattern.GameEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import observerPattern.Observable;
import observerPattern.Observer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Stage implements Observable {
    /** The game board, contains the towers the player has placed */
    public Tower[][] gameBoard;

    /** The list of alive entities in the borad. Alive means they must be rendered in the View controller */
    public List<Enemy> enemiesAlive;
    public List<Projectile> projectilesAlive;
    public EnemyFactory enemyFactory;
    public TowerFactory towerFactory;

    /** The amount of rows and columns in the gameBoard */
    public static int nrows = 7, mcols = 10;
    public StageNumero stageNumero;

    public int playerHealth;

    // A simple task containing the json data, (FAST, 2, 1) means a fast enemy must be spawned at row 2
    // and then we wait 1 second before continuing the spawn
    private SpawnObjectStack spawningStack;

    // To delay between two spawns, correspond to the second element of each tuple in spawning_list
    private double spawnDelay;
    // To calculate whether we passed the delay
    private double previousTimer;

    private double moneyGenerationDelay;
    private double previousMoneyGenerationTimer;

    public int playerMoney;

    private List<Observer> observers = new ArrayList<>();

    private ViewController viewController;


    public Stage(StageNumero stageNumero, ViewController viewController) throws IOException {
        this.gameBoard = new Tower[nrows][mcols];
        this.stageNumero = stageNumero;
        enemiesAlive = new ArrayList<Enemy>();
        projectilesAlive = new ArrayList<Projectile>();
        this.enemyFactory = new EnemyFactory();
        this.towerFactory = new TowerFactory();
        this.playerHealth = 5;
        this.spawnDelay = 0f;
        this.spawningStack = initSpawnStack();
        this.previousMoneyGenerationTimer = System.nanoTime();
        this.moneyGenerationDelay = (5f/Game.CURRENT_SPEED_FACTOR) * 1000000000.0;;
        setPlayerMoney(1000);
        addObserver(viewController);
    }

    public void setPlayerMoney(int i) {
        this.playerMoney = i;
    }

    public int getPlayerMoney() {
        return this.playerMoney;
    }

    public void update(){
        updateEntities();
        spawnEnemies();
        checkCollisions();
        generateMoney();
    }

    /** Natural money generation, not via money tower */
    private void generateMoney() {
        if(System.nanoTime() - previousMoneyGenerationTimer >= moneyGenerationDelay/Game.CURRENT_SPEED_FACTOR){
            // Delay has passed, we can generate money
            System.out.println("Generating money");
            this.playerMoney += 25;
            this.previousMoneyGenerationTimer = System.nanoTime();
        }
    }

    /**
     * Spawns an enemy in the spawn stack and wait the corresponding delay before spawning the next one
     */
    private void spawnEnemies() {
        if(spawningStack.isEmpty()) return;

        if(System.nanoTime() - previousTimer >= spawnDelay){
            // Delay has passed, we can go on to the next enemy to spawn
            SpawnObject so = spawningStack.pop();
            EnemyType enemyType = so.type;
            int row = so.row;
            double delay = (so.delay * 1000000000.0)/Game.CURRENT_SPEED_FACTOR; // seconds to nanoseconds, and taking into account the in-game speed

            switch(enemyType){
                case WEAK -> {
                    Enemy e = enemyFactory.createWeakEnemy(row);
                    enemiesAlive.add(e);
                }
                case POLYVALENT -> {
                    Enemy e = enemyFactory.createPolyvalentEnemy(row);
                    enemiesAlive.add(e);
                }
                case FAST -> {
                    Enemy e = enemyFactory.createFastEnemy(row);
                    enemiesAlive.add(e);
                }
                case TANK -> {
                    Enemy e = enemyFactory.createTankEnemy(row);
                    enemiesAlive.add(e);
                }
                default -> System.err.println("Wrong enemy type");
            }

            this.spawnDelay = delay;
            this.previousTimer = System.nanoTime();
        }

    }

    /** Occurs when Game receives message from ViewController, means the user tries to place a turret*/
    public void spawnTower(Object eventData, TowerType towerType) {
        Point location = (Point) eventData;
        int locationX = location.x;
        int locationY = location.y;
        int row = getRowFromY(locationY);
        int column = getColumnFromX(locationX);

        if(row < 0 || row >= nrows || column < 0 || column >= mcols){
            // The user tried to place a tower outside the game board
            return;
        }

        switch(towerType){
            case ATTACK -> {
                if(this.playerMoney < Tower.ATTACK_TOWER_PRICE) return;
                this.playerMoney -= Tower.ATTACK_TOWER_PRICE;
                this.gameBoard[row][column] = towerFactory.createAttackTower(row, column);
            }
            case DEFENSIVE -> {
                if(this.playerMoney < Tower.DEFENSIVE_TOWER_PRICE) return;
                this.playerMoney -= Tower.DEFENSIVE_TOWER_PRICE;
                this.gameBoard[row][column] = towerFactory.createDefensiveTower(row, column);
            }
            case MONEY -> {
                if(this.playerMoney < Tower.MONEY_TOWER_PRICE) return;
                this.playerMoney -= Tower.MONEY_TOWER_PRICE;
                this.gameBoard[row][column] = towerFactory.createMoneyTower(row, column);
            }
            case MULTI -> {
                if(this.playerMoney < Tower.MULTI_TOWER_PRICE) return;
                this.playerMoney -= Tower.MULTI_TOWER_PRICE;
                this.gameBoard[row][column] = towerFactory.createMultiTower(row, column);
            }
            case GLOBAL -> {
                if(this.playerMoney < Tower.GLOBAL_TOWER_PRICE) return;
                this.playerMoney -= Tower.GLOBAL_TOWER_PRICE;
                this.gameBoard[row][column] = towerFactory.createGlobalTower(row, column);
            }


        }
    }

    /**
     * Returns a new spawnstack corresponding to the parsed json.
     * Contains all the enemies to create in the stage, their corresponding row as well as the time to wait between two spawns.
     */
    private SpawnObjectStack initSpawnStack() throws IOException {
        String jsonPath = "";
        switch(this.stageNumero){
            case STAGE1 -> jsonPath = "config/stage1.json";
            case STAGE2 -> jsonPath = "config/stage2.json";
            case STAGE3 -> jsonPath = "config/stage3.json";
            default -> throw new RuntimeException("Wrong stage level specified");
        }

        // With this JSON implementation we need first to convert the string to JSON
        JSONArray enemiesJSONArray = new JSONArray();
        try {
            File jsonfile = new File(jsonPath);
            String jsonString = Files.readString(jsonfile.toPath());
            enemiesJSONArray = new JSONObject(jsonString).getJSONArray("stage_enemies");
        } catch(IOException e){
            e.printStackTrace();
        }

        SpawnObjectStack stack = new SpawnObjectStack();

        // We fetch all the information to build a SpawnObject from the json
        for(int i = enemiesJSONArray.length()-1; i > -1; i--){
            // We iterate from end to start as we want our enemies to be spawned in the json order
            // (we use a stack, so we start by pushing the last element)
            EnemyType type = EnemyType.valueOf(enemiesJSONArray.getJSONObject(i).getString("type"));
            int row = enemiesJSONArray.getJSONObject(i).getInt("row");
            float delay = enemiesJSONArray.getJSONObject(i).getFloat("delay");

            SpawnObject so = new SpawnObject(type, row, delay);
            stack.push(so);
        }

        System.out.println("Successfully built the stack with size:" + stack.length());
        stack.print();
        return stack;
    }

    /**
     * Will update all entities from the entities array, containing all the entities alive (enemies, towers, projectiles)
     */
    private void updateEntities() {
        List<Enemy> enemiesToRemove = new ArrayList<Enemy>();
        // Update all entities
        for(Entity e : enemiesAlive){
            if(e != null) {
                if(e instanceof Enemy) {
                    Enemy enemy = (Enemy) e;
                    if(((Enemy) e).toRemove()){
                        // We need to remove the enemy from the list of entities outside the loop
                        enemiesToRemove.add(enemy);
                    }
                }
                e.update();
            }
        }

        for(Enemy e : enemiesToRemove) {
            enemiesAlive.remove(e);
        }

        // Update all towers
        for(int i = 0; i<nrows; i++){
            for(int j = 0; j<mcols; j++){
                if(gameBoard[i][j] != null){
                    if(!gameBoard[i][j].isAlive()){
                        gameBoard[i][j] = null;
                    }
                    else{
                        gameBoard[i][j].update();
                    }
                }
            }
        }

        // Update all projectiles
        List<Projectile> projectilesToRemove = new ArrayList<Projectile>();
        for(Projectile p : projectilesAlive){
            if(p != null) {
                if(p.toRemove()){
                    // We need to remove the projectile from the list of entities outside the loop
                    projectilesToRemove.add(p);
                }
                p.update();
            }
        }
        for(Projectile p : projectilesToRemove) {
            projectilesAlive.remove(p);
        }
    }

    private void checkCollisions(){
        checkCollisionsProjectilesEnemies();
        checkCollisionsEnemiesTowers();
    }

    /** Will check for every projectile alive if it's inside the hitbox of any alive enemy */
    private void checkCollisionsProjectilesEnemies() {
        for(Projectile p : projectilesAlive){
            if(p != null && p.inTheWindow) {
                for (Enemy e : enemiesAlive) {
                    if (e != null && e.isUsed) {
                        if (p.getHitbox().intersects(e.getHitbox())) {
                            // Collision between projectile and enemy
                            e.setHealth(e.getHealth() - p.getDamage());
                            p.inTheWindow = false;
                            switch(e.getType()){
                                // We play the correct hitting sound depending on the enemy type
                                case WEAK -> Game.getInstance().audioPlayer.playEffect(AudioPlayer.WEAK_HURT_SFX);
                                case FAST -> Game.getInstance().audioPlayer.playEffect(AudioPlayer.FAST_HURT_SFX);
                                case TANK -> Game.getInstance().audioPlayer.playEffect(AudioPlayer.TANK_HURT_SFX);
                                case POLYVALENT -> Game.getInstance().audioPlayer.playEffect(AudioPlayer.POLYVALENT_HURT_SFX);
                            }
                        }
                    }
                }
            }
        }

    }

    /** Will check for every enemy alive if it's inside the hitbox of any tower */
    private void checkCollisionsEnemiesTowers() {
        for(Enemy e : enemiesAlive){
            if(e != null && e.isUsed) {
                for (int i = 0; i < nrows; i++) {
                    for (int j = 0; j < mcols; j++) {
                        if (gameBoard[i][j] != null) {
                            if (e.getHitbox().intersects(gameBoard[i][j].getHitbox())) {
                                // Collision between enemy and tower
                                e.setAttacking(true);
                                e.setTarget(gameBoard[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }



    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }


    @Override
    public void notifyObservers(GameEvent event) {
        for(Observer observer : observers) {
            observer.receiveEventNotification(event);
        }
    }

    public void generateEvent(String eventType, Object eventData) {
        GameEvent event = new GameEvent(eventType, eventData);
        notifyObservers(event);
    }
    public static int getRowFromY(int y){
        return ((int) (y / ((ViewController.HEIGHT*0.85) / nrows)))-1;
    }

    private int getColumnFromX(int x){
        return x / (ViewController.WIDTH / mcols);
    }

}
