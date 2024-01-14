package gameLogic;

import gameLogic.entity.*;
import gameLogic.spawn.SpawnObject;
import gameLogic.spawn.SpawnObjectStack;
import org.json.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Stage {
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

    public int playerMoney;


    public Stage(StageNumero stageNumero) throws IOException {
        this.gameBoard = new Tower[nrows][mcols];
        this.stageNumero = stageNumero;
        enemiesAlive = new ArrayList<Enemy>();
        projectilesAlive = new ArrayList<Projectile>();
        this.enemyFactory = new EnemyFactory();
        this.towerFactory = new TowerFactory();
        this.playerHealth = 5;
        this.spawnDelay = 0f;
        this.spawningStack = initSpawnStack();
        setPlayerMoney(100);
        spawnTower();
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

    //temporary, just for testing
    private void spawnTower(){
        Tower tower = towerFactory.createDefensiveTower(0,0);
        gameBoard[1][0] = tower;
        tower = towerFactory.createAttackTower(2,3);
        gameBoard[2][0] = tower;
        tower = towerFactory.createMoneyTower(3,0);
        gameBoard[3][0] = tower;
        tower = towerFactory.createMultiTower(4,0);
        gameBoard[4][0] = tower;
        tower = towerFactory.createGlobalTower(7,7);
        gameBoard[5][0] = tower;

        System.out.println("Spawning towers");
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
                    gameBoard[i][j].update();
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
                    System.out.println("Projectile removed");
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
    private void checkCollisionsEnemiesTowers() {
        for(Projectile p : projectilesAlive){
            if(p != null && p.inTheWindow) {
                for (Enemy e : enemiesAlive) {
                    if (e != null && e.isUsed) {
                        if (p.getHitbox().intersects(e.getHitbox())) {
                            // Collision between projectile and enemy
                            e.setHealth(e.getHealth() - p.getDamage());
                            p.inTheWindow = false;
                        }
                    }
                }
            }
        }

    }

    private void checkCollisionsProjectilesEnemies() {
    }

}
