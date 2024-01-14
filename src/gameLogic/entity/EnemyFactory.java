package gameLogic.entity;

import gameLogic.Stage;
import gameView.ViewController;
import gameView.panels.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            buildMainStats(e, 10, 100, 100, 100, 3);
            setCoords(e, row, 0);
            setSprites(e, "assets/sprites/enemies/weak.png", 8);
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
            buildMainStats(e, 10, 100, 100, 10, 10);
            setCoords(e, row, 0);
            setSprites(e, "assets/sprites/enemies/tank.png", 8);
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
            buildMainStats(e, 10, 100, 100, 10, 10);
            setCoords(e, row, 0);
            setSprites(e, "assets/sprites/enemies/fast.png", 8);
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
            buildMainStats(e, 10, 100, 100, 10, 10);
            setCoords(e, row, 0);
            setSprites(e, "assets/sprites/enemies/polyvalent.png", 8);
            e.setHitbox();

            return e;
        }
        return null;
    }

    private void buildMainStats(Enemy e, int speed, int health, int maxHealth, int damage, int firingRate){
        e.setSpeed(speed);
        e.setHealth(health);
        e.setMaxHealth(maxHealth);
        e.setDamage(damage);
        e.setFiringRate(firingRate);
    }

    private void setCoords(Enemy e, int row, int column){
        e.setX(ViewController.WIDTH);
        // We set the Y coordinate of the enemy to the center of the entity's row
        row = row % Stage.nrows;
        e.setY(GameScreen.rowToY(row));
    }

    private void setSprites(Enemy e, String path, int NSPRITES){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(path));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        e.setSpriteSheet(img);
        e.setSpriteIndex(0);
        e.setNSPRITES(NSPRITES);
    }


}
