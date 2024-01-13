package gameLogic.entity;

import gameLogic.Game;
import gameLogic.Stage;
import gameView.ViewController;
import gameView.panels.GameScreen;

import java.awt.*;


public class Tower extends Entity{

    private int health, maxHealth;
    private int damage;
    private int firingRate;
    private boolean canShoot;
    private boolean canBlock;
    private int price;

    public void update(){
        super.update();
        // TODO! Make the tower shoot
    }

    public boolean toRemove(){
        return health <= 0;
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


    public void setHitbox(){
        int cellWidth = GameScreen.calculateCellWidth();
        int cellHeight = GameScreen.calculateCellHeight();

        int hitboxWidth = (int) (cellWidth*0.8), hitboxHeight = cellHeight/2;
        super.setHitbox(5000, 5000, hitboxWidth, hitboxHeight);
    }

}
