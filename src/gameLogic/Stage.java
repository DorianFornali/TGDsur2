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
    public static List<Entity> entities;
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


    public Stage(StageNumero stageNumero) throws IOException {
        this.gameBoard = new Tower[nrows][mcols];
        this.stageNumero = stageNumero;
        entities = new ArrayList<Entity>();
        this.enemyFactory = new EnemyFactory();
        this.playerHealth = 5;
        this.spawnDelay = 0f;
        this.spawningStack = initSpawnStack();
        spawnTower();
    }

    public void update(){
        updateEntities();
        spawnEnemies();

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

            System.out.println("SPAWNING AN ENEMY");
            switch(enemyType){
                case WEAK -> {
                    Enemy e = enemyFactory.createWeakEnemy(row);
                    entities.add(e);
                    System.out.println("Spawning weak enemy");
                }
                case POLYVALENT -> {
                    Enemy e = enemyFactory.createPolyvalentEnemy(row);
                    entities.add(e);
                    System.out.println("Spawning polyvalent enemy");
                }
                case FAST -> {
                    Enemy e = enemyFactory.createFastEnemy(row);
                    entities.add(e);
                    System.out.println("Spawning fast enemy");
                }
                case TANK -> {
                    Enemy e = enemyFactory.createTankEnemy(row);
                    entities.add(e);
                    System.out.println("Spawning tank enemy");
                }
                default -> System.err.println("Wrong enemy type");
            }

            this.spawnDelay = delay;
            this.previousTimer = System.nanoTime();
            System.out.println("DONE SPAWNING ONE ENEMY, GOING TO NEXT ONE");
        }

    }

    //temporary, just for testing
    private void spawnTower(){
        Tower tower = towerFactory.createDefensiveTower(1,0);
        entities.add(tower);
        Tower tower1 = towerFactory.createAttackTower(2,0);
        entities.add(tower1);
        Tower tower2 = towerFactory.createRecoveryTower(3,0);
        entities.add(tower2);
        Tower tower3 = towerFactory.createMultiTower(4,0);
        entities.add(tower3);
        Tower tower4 = towerFactory.createGlobalTower(5,0);
        entities.add(tower4);
        System.out.println("Spawning tower");
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
        for(Entity e : entities){
            if(e != null) {
                if(e instanceof Enemy) {
                    Enemy enemy = (Enemy) e;
                    if(((Enemy) e).toRemove()){
                        // We need to remove the enemy from the list of entities outside the loop
                        enemiesToRemove.add(enemy);
                    }
                }

                if(e instanceof Tower) {
                    Tower tower = (Tower) e;
                    if(((Tower) e).toRemove()){
                        // We need to remove the tower from the list of entities outside the loop
                        // We also need to remove it from the gameboard
                        entities.remove(tower);
                    }
                }
                e.update();
            }
        }

        for(Enemy e : enemiesToRemove) {
            entities.remove(e);
        }

        // Update all towers
        for(int i = 0; i<nrows; i++){
            for(int j = 0; j<mcols; j++){
                if(gameBoard[i][j] != null){
                    gameBoard[i][j].update();
                }
            }
        }
    }

}
