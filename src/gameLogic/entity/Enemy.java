package gameLogic.entity;

import gameLogic.Game;
import gameLogic.Stage;
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
            // TODO! Change spritesheet for a more "damaged" one
        }

        // Moving the enemy from right to left
        setX(getX() - (float) speed /10);

        if(getX() < 0){
            health = -1;
            Game.getInstance().getCurrentStage().playerHealth--;
        }



    }

    public void initialize(){
        this.isUsed = true;
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

    public boolean toRemove() {
    	return health <= 0 || getX() < 0;
    }


}
