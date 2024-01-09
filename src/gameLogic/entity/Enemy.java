package gameLogic.entity;

import gameLogic.Game;
import gameView.ViewController;

import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    // Whether the Enemy is being used by the pool of object or not
    public boolean isUsed;

    // Enemy's index in the pool of object
    public int index;

    private int health, maxHealth;
    private int speed;

    public Enemy(int index){
        this.index = index;
        this.isUsed = false;
    }

    /** Initialization of the enemy, called when the factory decides to create one*/
    public void initialize() {
        this.isUsed = true;
        setX(ViewController.WIDTH);
        setY(ViewController.HEIGHT/2); // TODO set Y corresponding to the row accorded to the enemy
    }

    /** Used when the enemy dies, resets its components to a blank state so
     * the object can be reused */
    public void reset() {
        this.isUsed = false;
    }

    public void update(){
        if(health <= 0){
            // Enemy dies
            EnemyPool.getInstance().freeSpace(index);
        }
        else if(health <= maxHealth/2){
            // Enemy is hurt
            // TODO! Change spritesheet for a more "damaged" one
        }

        // Moving the enemy from right to left
        System.out.println("Enemy moving");
        setX(getX() - (float) speed /100);
        if(getX() < 0){
            // Enemy reached the end of the board
            Game.getInstance().getCurrentStage().playerHealth--;
            EnemyPool.getInstance().freeSpace(index);
        }
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setHealth(int i) {
        this.health = i;
    }

    public void setMaxHealth(int i) {
        this.maxHealth = i;
    }
}
