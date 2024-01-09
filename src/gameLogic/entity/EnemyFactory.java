package gameLogic.entity;

/** Factory "creating" dynamically new enemies, will call the pool of object */
public class EnemyFactory {
    private EnemyPool pool;

    public EnemyFactory(){
        this.pool = EnemyPool.getInstance();
    }
    public Enemy createEnemy(){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.out.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }
}
