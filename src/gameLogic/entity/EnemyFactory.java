package gameLogic.entity;

import gameLogic.Stage;
import gameView.AssetManager;
import gameView.ViewController;
import gameView.panels.GameScreen;

import java.awt.image.BufferedImage;


/** Factory "creating" dynamically new enemies, will call the pool of object */
public class EnemyFactory {
    private EnemyPool pool;

    public EnemyFactory(){
        this.pool = EnemyPool.getInstance();
    }
    public Enemy createWeakEnemy(int row){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            buildMainStats(e, 2, 200, 200, 25, 1, EnemyType.WEAK);
            setCoords(e, row, 0);
            setSprites(e, "weakEnemy", 8);
            e.setHitbox();

            return e;
        }
        return null;
    }

    public Enemy createTankEnemy(int row){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            buildMainStats(e, 10, 100, 100, 10, 10, EnemyType.TANK);
            setCoords(e, row, 0);
            setSprites(e, "tankEnemy", 8);
            e.setHitbox();

            return e;
        }
        return null;
    }

    public Enemy createFastEnemy(int row){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            buildMainStats(e, 10, 100, 100, 10, 10, EnemyType.FAST);
            setCoords(e, row, 0);
            setSprites(e, "fastEnemy", 8);
            e.setHitbox();

            return e;
        }
        return null;
    }

    public Enemy createPolyvalentEnemy(int row){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            buildMainStats(e, 10, 100, 100, 10, 10, EnemyType.POLYVALENT);
            setCoords(e, row, 0);
            setSprites(e, "polyvalentEnemy", 8);
            e.setHitbox();

            return e;
        }
        return null;
    }

    private void buildMainStats(Enemy e, int speed, int health, int maxHealth, int damage, int firingRate, EnemyType type){
        e.setSpeed(speed);
        e.setHealth(health);
        e.setMaxHealth(maxHealth);
        e.setDamage(damage);
        e.setFiringRate(firingRate);
        e.setType(type);
    }

    private void setCoords(Enemy e, int row, int column){
        e.setX(ViewController.WIDTH);
        // We set the Y coordinate of the enemy to the center of the entity's row
        row = row % Stage.nrows;
        e.setY(GameScreen.rowToY(row));
    }

    private void setSprites(Entity e, String type, int NSPRITES){
        BufferedImage img = AssetManager.getInstance().getSprite(type);

        e.setSpriteSheet(img);
        e.setSpriteIndex(0);
        e.setNSPRITES(NSPRITES);
    }


}
