package gameLogic.entity;

import gameLogic.Stage;
import gameView.panels.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TowerFactory {

    // Tower that tank (coconut in PVZ)
    public Tower createDefensiveTower(int row, int column) {
        Tower tower = new Tower();

        System.out.println("Creating Defensive tower");
        buildMainStats(tower, 400, 200, 0, 1, 10,false, true, 100);
        setCoords(tower, row, column);
        setSprites(tower, "assets/sprites/Tower/defensive.png", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.DEFENSIVE);

        return tower;
    }

    // Tower that generates money (sunflower in PVZ)
    public Tower createMoneyTower(int row, int column) {
        Tower tower = new Tower();

        System.out.println("Creating Money tower");
        buildMainStats(tower, 50, 50, 25, 3, 10,true, false, 50);
        setCoords(tower, row, column);
        setSprites(tower, "assets/sprites/Tower/money.png", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.MONEY);

        return tower;
    }

    // Tower that attack (peashooter in PVZ)
    public Tower createAttackTower(int row, int column) {
        Tower tower = new Tower();

        System.out.println("Creating Attack tower");
        buildMainStats(tower, 100, 100, 10, 1, 10,true, true, 100);
        setCoords(tower, row, column);
        setSprites(tower, "assets/sprites/Tower/attack.png", 8);
        tower.setHitbox();
        tower.setTowerType(TowerType.ATTACK);

        return tower;
    }

    // Tower that attack on tree line at the same time (triple shooter in PVZ)
    public Tower createMultiTower(int row, int column){
        Tower tower = new Tower();

        System.out.println("Creating Multi tower");
        buildMainStats(tower, 150, 150, 10, 5, 10,true, true, 325);
        setCoords(tower, row, column);
        setSprites(tower, "assets/sprites/Tower/multi.png", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.MULTI);

        return tower;
    }

    //Tower that can attack every enemy on the map at the same time
    public Tower createGlobalTower(int row, int column){
        Tower tower = new Tower();

        System.out.println("Creating Global tower");
        buildMainStats(tower, 200, 200, 3, 5, 10,true, true, 500);
        setCoords(tower, row, column);
        setSprites(tower, "assets/sprites/Tower/global.png", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.GLOBAL);

        return tower;
    }

    private void buildMainStats(Tower t, int health, int maxHealth, int damage, int firingRate, float speed, boolean canShoot, boolean canBlock, int price){
        t.setHealth(health);
        t.setMaxHealth(maxHealth);
        t.setDamage(damage);
        t.setFiringRate(firingRate);
        t.setSpeed(speed);
        t.setCanShoot(canShoot);
        t.setCanBlock(canBlock);
        t.setPrice(price);
    }

    private void setCoords(Tower t, int row, int column){
        // We set the X coordinate of the tower to the center of the entity's column
        column = column % Stage.mcols;
        t.setX(GameScreen.columnToX(column));

        // We set the Y coordinate of the tower to the center of the entity's row
        row = row % Stage.nrows;
        t.setY(GameScreen.rowToY(row));
    }

    private void setSprites(Tower t, String path, int NSPRITES){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(path));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        t.setSpriteSheet(img);
        t.setSpriteIndex(0);
        t.setNSPRITES(NSPRITES);
    }

}
