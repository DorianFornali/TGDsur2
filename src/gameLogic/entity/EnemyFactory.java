package gameLogic.entity;

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
            System.out.println("Unable to allocate enemy, all are used");
        }
        else{
            System.out.println("Creating weak enemy");
            e.setSpeed(20);
            e.setX(ViewController.WIDTH);
            e.setY((float) ViewController.HEIGHT /2);
            e.setHealth(100);
            e.setMaxHealth(100);

            // We read the spritesheet from the assets/sprites/enemies/weakEnemySpritesheet.png and set it
            // as the enemy's spritesheet
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("assets/sprites/enemies/weakEnemySprite.png"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            e.setSpriteSheet(img);
            e.setSpriteHeight(100);
            e.setSpriteWidth(100);
            return e;
        }
        return null;
    }

    public Enemy createTankEnemy(){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.out.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    public Enemy createFastEnemy(){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.out.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    public Enemy createPolyvalentEnemy(){
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
