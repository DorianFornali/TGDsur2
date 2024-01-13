package gameLogic.entity;

import gameLogic.Game;
import gameLogic.Stage;
import gameView.ViewController;
import gameView.panels.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Tower extends Entity{

    private Game game = Game.getInstance();
    private int health, maxHealth;
    private int damage;
    private boolean canShoot;
    private boolean canBlock;
    private int price;
    private TowerType towerType;


    /** Updates the tower, will call a function corresponding of the tower's type 
     * We choose to do it that way instead of one class = one tower 
     * */
    public void update(){
        super.update();
        // TODO! Make the tower shoot
        switch(this.towerType){
            case ATTACK:
                updateAttackTower();
                break;
            case MULTI:
                updateMultiTower();
                break;
            case GLOBAL:
                updateGlobalTower();
                break;
            case DEFENSIVE:
                updateDefensiveTower();
                break;
            case MONEY:
                updateMoneyTower();
                break;
        }
    }

    /** The update function for the money tower */
    private void updateMoneyTower() {
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            // The tower can "shoot", in this case it simply generates money
            game.getCurrentStage().setPlayerMoney(game.getCurrentStage().getPlayerMoney() + 15);
            //TODO! Do some visual effect to show the player that the tower generated money -> the GameScreen must do it
            //TODO! send message to GameScreen
            previousFiring = System.nanoTime();
        }
    }

    private void updateDefensiveTower() {
        //TODO!
    }

    private void updateGlobalTower() {
        //TODO!
    }

    private void updateMultiTower() {
        //TODO!
    }

    private void updateAttackTower() {
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            shootProjectile();
            previousFiring = System.nanoTime();
        }
    }

    public void takeDamage(int damage){
        health -= damage;
    }


    public void setCanShoot(boolean canShoot){
        this.canShoot = canShoot;
    }

    public void setCanBlock(boolean canBlock){
        this.canBlock = canBlock;
    }

    public void setPrice(int price){
        this.price = price;
    }
    
    public void setTowerType(TowerType towerType){
        this.towerType = towerType;
    }


    public void setHitbox(){
        int cellWidth = GameScreen.calculateCellWidth();
        int cellHeight = GameScreen.calculateCellHeight();

        int hitboxWidth = (int) (cellWidth*0.8), hitboxHeight = cellHeight/2;
        super.setHitbox(5000, 5000, hitboxWidth, hitboxHeight);
    }

    /** Creates a projectile at the tower location */
    private void shootProjectile(){
        Projectile p = new Projectile();
        p.setX(getX() + (float) getHitbox().getWidth() /2);
        p.setY((float) (getY() + getHitbox().getHeight()/2));
        p.setHitbox(-500, -500, GameScreen.calculateCellWidth()/4, GameScreen.calculateCellHeight()/4);
        p.setSpeed(getSpeed());
        p.setDamage(getDamage());
        game.getCurrentStage().projectilesAlive.add(p);
    }

}
