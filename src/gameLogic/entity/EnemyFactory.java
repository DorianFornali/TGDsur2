package gameLogic.entity;

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
            e.setSpeed(10);
            e.setHealth(100);
            e.setMaxHealth(100);
            e.setX(ViewController.WIDTH);
            // We set the Y coordinate of the enemy to the center of the entity's row
            row = row % Stage.nrows;
            e.setY(rowToY(row));

            // We read the spritesheet from the assets/sprites/enemies/weakEnemySpritesheet.png and set it
            // as the enemy's spritesheet
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("assets/sprites/enemies/fast.png"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            e.setSpriteSheet(img);
            e.setSpriteIndex(0);
            e.setNSPRITES(8);
            
            int cellWidth = calculateCellWidth();
            int cellHeight = calculateCellHeight();

            int hitboxWidth = cellWidth/3, hitboxHeight = cellHeight/2;
            e.setHitbox(0, 0, hitboxWidth, hitboxHeight);
            // There is no need to give the hitbox a correct position for now as it'll get updated in the e.update() method
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
            return e;
        }
        return e;
    }

    public Enemy createFastEnemy(int row){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    public Enemy createPolyvalentEnemy(int row){
        Enemy e = pool.allocateEnemy();
        if(e == null){
            System.err.println("Unable to allocate enemy, all are used");
        }
        else{
            return e;
        }
        return e;
    }

    /** Converts a row to coordinates */
    private float rowToY(int row){
        return (float) (ViewController.HEIGHT*0.15 + (row-1) * ((ViewController.HEIGHT-ViewController.HEIGHT*0.15)/Stage.nrows));
    }

    /** Fetch information on the gameboard to determine the height of a game cell, to calculate the hitbox's size*/
    private int calculateCellHeight() {
        int n = Stage.nrows;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int gridHeight = panelHeight - topHeight;
        int cellHeight = gridHeight / n;
        return cellHeight;
    }

    /** Fetch information on the gameboard to determine the width of a game cell, to calculate the hitbox's size*/
    private int calculateCellWidth() {
        int m = Stage.mcols;
        int panelWidth = ViewController.WIDTH;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int cellWidth = panelWidth / m;
        return cellWidth;
    }
}
