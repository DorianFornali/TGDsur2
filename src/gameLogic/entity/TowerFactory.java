package gameLogic.entity;

import gameLogic.Stage;
import gameView.AssetManager;
import gameView.panels.GameScreen;

import java.awt.image.BufferedImage;


public class TowerFactory {

    // Tower that tank (coconut in PVZ)
    public Tower createDefensiveTower(int row, int column) {
        Tower tower = new Tower();

        System.out.println("Creating Defensive tower");
        buildMainStats(tower, 400, 200, 0, 1, 10,false, true, 100);
        setCoords(tower, row, column);
        setSprites(tower, "defensiveTower", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.DEFENSIVE);
        tower.setisAlive(true);
        return tower;
    }

    // Tower that generates money (sunflower in PVZ)
    public Tower createMoneyTower(int row, int column) {
        Tower tower = new Tower();

        System.out.println("Creating Money tower");
        buildMainStats(tower, 300, 300, 25, 3, 10,true, false, 50);
        setCoords(tower, row, column);
        setSprites(tower, "moneyTower", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.MONEY);
        tower.setisAlive(true);
        return tower;
    }

    // Tower that attack (peashooter in PVZ)
    public Tower createAttackTower(int row, int column) {
        Tower tower = new Tower();

        System.out.println("Creating Attack tower");
        buildMainStats(tower, 100, 100, 10, 1, 10,true, true, 100);
        setCoords(tower, row, column);
        setSprites(tower, "attackTower", 8);
        tower.setHitbox();
        tower.setTowerType(TowerType.ATTACK);
        tower.setisAlive(true);
        return tower;
    }

    // Tower that attack on tree line at the same time (triple shooter in PVZ)
    public Tower createMultiTower(int row, int column){
        Tower tower = new Tower();

        System.out.println("Creating Multi tower");
        buildMainStats(tower, 150, 150, 10, 5, 10,true, true, 5);
        setCoords(tower, row, column);
        setSprites(tower, "multiTower", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.MULTI);
        tower.setisAlive(true);
        return tower;
    }

    // Tower that can attack every enemy on the map at the same time
    public Tower createGlobalTower(int row, int column){
        Tower tower = new Tower();

        System.out.println("Creating Global tower");
        buildMainStats(tower, 200, 200, 50, 5, 10,true, true, 5);
        setCoords(tower, row, column);
        setSprites(tower, "globalTower", 1);
        tower.setHitbox();
        tower.setTowerType(TowerType.GLOBAL);
        tower.setisAlive(true);
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

    private void setSprites(Tower t, String type, int NSPRITES){
        BufferedImage img = AssetManager.getInstance().getSprite(type);
        t.setSpriteSheet(img);
        t.setSpriteIndex(0);
        t.setNSPRITES(NSPRITES);
    }

}
