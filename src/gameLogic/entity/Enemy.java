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

    // Whether the enemy is attacking or not a tower
    private boolean isAttacking;

    // The tower the enemy is attacking
    private Tower target;

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

        updateTargetState();

        attack();

        if(getHealth() <= 0){
            // Enemy dies
            reset();
        }
        else if(getHealth() <= getMaxHealth()/2){
            // Enemy is hurt
            // TODO! Change spritesheet for a more "damaged" one
        }

        // Moving the enemy from right to left
        if(!isAttacking)
            setX(getX() - getSpeed() /10);

        if(hitbox.x < 0){
            setHealth(-1);
            Game.getInstance().getCurrentStage().playerHealth--;
        }

    }

    /** Checks if the current target is dead or not */
    private void updateTargetState() {
        if(target != null && !target.isAlive()){
            // The target is dead, the enemy can move again
            target = null;
            isAttacking = false;
        }
    }


    /** Attack function with firingRate as delay */
    private void attack() {
        if(!isAttacking || target == null) return;
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            // The enemy can "shoot", in this case it simply attacks the tower
            target.setHealth(target.getHealth() - damage);
            previousFiring = System.nanoTime();
        }
    }

    public void initialize(){
        this.isUsed = true;
    }

    public boolean toRemove() {
    	return getHealth() <= 0 || hitbox.x < 0;
    }

    public void setHitbox() {
        int cellWidth = GameScreen.calculateCellWidth();
        int cellHeight = GameScreen.calculateCellHeight();

        int hitboxWidth = cellWidth/3, hitboxHeight = cellHeight/2;
        // Initial position doesn't matter as it'll get updated in the update() method
        // So we put it in a place where it won't collide with anything
        super.setHitbox(10000, 10000, hitboxWidth, hitboxHeight);
    }

    public void setTarget(Tower target) {
    	this.target = target;
    }

    public void setAttacking(boolean isAttacking) {
    	this.isAttacking = isAttacking;
    }

    public boolean isAttacking() {
    	return isAttacking;
    }
}
