package gameLogic.entity;

import gameLogic.Game;

/** The singleton pool of objects for the game's enemies */
public class EnemyPool {
    private static EnemyPool poolInstance;
    private static final int poolSize = 100;
    public int size;
    private Enemy[] pool;
    public EnemyPool(int n){
        this.size = n;
        initPool();
    }

    public static EnemyPool getInstance() {
        if(poolInstance == null) {
            poolInstance = new EnemyPool(poolSize);
        }
        return poolInstance;
    }

    /** Initializes the pool of object, creates n blank enemies */
    private void initPool() {
        for(int i = 0; i<size; i++){
            pool[i] = new Enemy(i);
        }
    }

    public Enemy allocateEnemy(){
        for(int i = 0; i<size; i++){
            if(!pool[i].isUsed){
                pool[i].initialize();
                return pool[i];
            }
        }
        return null;
    }

    public void freeSpace(int index){
        // We reset the enemy so it can be reused later
        pool[index].isUsed = false;
        pool[index].reset();
    }

}