package gameLogic.entity;

import gameLogic.Game;
import gameLogic.Stage;
import gameView.ViewController;
import gameView.panels.GameScreen;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    // Whether the Enemy is being used by the pool of object or not
    public boolean isUsed;

    // Enemy's index in the pool of object
    public int index;

    private int health, maxHealth;

    public Enemy(int index){
        this.index = index;
        this.isUsed = false;
    }


    /** Used when the enemy dies, resets its components to a blank state so
     * the object can be reused */
    public void reset() {
        this.isUsed = false;
    }

    public void update(){
        if(!isUsed) return;
        super.update();

        if(health <= 0){
            // Enemy dies
            System.out.println("Enemy died");
            reset();
        }
        else if(health <= maxHealth/2){
            // Enemy is hurt
            System.out.println("ENEMY HURT BADLY");
            // TODO! Change spritesheet for a more "damaged" one
        }

        // Moving the enemy from right to left
        setX(getX() - getSpeed() /10);

        if(hitbox.x < 0){
            health = -1;
            Game.getInstance().getCurrentStage().playerHealth--;
        }

    }

    public void initialize(){
        this.isUsed = true;
    }

    public void setHealth(int i) {
        this.health = i;
    }

    public void setMaxHealth(int i) {
        this.maxHealth = i;
    }

    public boolean toRemove() {
    	return health <= 0 || hitbox.x < 0;
    }

    public void setHitbox() {
        int cellWidth = GameScreen.calculateCellWidth();
        int cellHeight = GameScreen.calculateCellHeight();

        int hitboxWidth = cellWidth/3, hitboxHeight = cellHeight/2;
        // Initial position doesn't matter as it'll get updated in the update() method
        // So we put it in a place where it won't collide with anything
        super.setHitbox(10000, 10000, hitboxWidth, hitboxHeight);
    }

    public int getHealth() {
        return health;
    }
}
