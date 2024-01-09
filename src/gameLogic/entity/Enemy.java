package gameLogic.entity;

public class Enemy extends Entity{
    // Whether the Enemy is being used by the pool of object or not
    public boolean isUsed;

    // Enemy's index in the pool of object
    public int index;

    public Enemy(int index){
        this.index = index;
        this.isUsed = false;
    }

    /** Initialization of the enemy, called when the factory decides to create one*/
    public void initialize() {
        this.isUsed = true;

    }

    /** Used when the enemy dies, resets its components to a blank state so
     * the object can be reused */
    public void reset() {
        this.isUsed = false;
    }
}
