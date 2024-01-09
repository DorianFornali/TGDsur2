package gameLogic.entity;

import gameLogic.Game;
import gameLogic.Stage;
import gameView.ViewController;

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
            System.out.println("Allocated enemy");
            e.setSpeed(20);
            e.setX(ViewController.WIDTH);
            e.setY((float) ViewController.HEIGHT /2);
            e.setHealth(100);
            e.setMaxHealth(100);
            e.setX(ViewController.WIDTH);
            // We set the Y coordinate of the enemy to the center of the entity's row
            e.setY(rowToY(row));

            // We read the spritesheet from the assets/sprites/enemies/weakEnemySpritesheet.png and set it
            // as the enemy's spritesheet
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("assets/sprites/enemies/weakEnemySprite.png"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            e.setSpriteSheet(img);
            return e;
        }
        return null;
    }

    public Enemy createTankEnemy(){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    public Enemy createFastEnemy(){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    public Enemy createPolyvalentEnemy(){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    private float rowToY(int row){
        return (float) (ViewController.HEIGHT*0.15 + row * ((ViewController.HEIGHT-ViewController.HEIGHT*0.15)/Stage.nrows));
    }
}
