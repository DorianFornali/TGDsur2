package gameLogic.entity;

import audio.AudioPlayer;
import gameLogic.Game;
import gameLogic.Stage;
import gameView.panels.GameScreen;

public class Enemy extends Entity{
    // Whether the Enemy is being used by the pool of object or not
    public boolean isUsed;

    // Enemy's index in the pool of object
    public int index;

    // Whether the enemy is attacking or not a tower
    private boolean isAttacking;

    // The tower the enemy is attacking
    private Tower target;
    private boolean canHurtPlayer;

    private EnemyType type;

    public Enemy(int index){
        this.index = index;
        this.isUsed = false;
        this.canHurtPlayer = true;
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

        if(hitbox.x < 0 && canHurtPlayer){
            this.canHurtPlayer = false;
            setHealth(-1);
            Stage stage = Game.getInstance().getCurrentStage();
            stage.playerHealth--;
            if (stage.playerHealth <=0 && Game.IN_GAME){
                stage.generateEvent("GAME_OVER", null);
                stage.clearStage();
            }
        }

        // Moving the enemy from right to left
        if(!isAttacking)
            setX(getX() - getSpeed() /10);


        if(getHealth() <= 0){
            // Enemy dies
            reset();
            playDeathSound();
        }
        else if(getHealth() <= getMaxHealth()/2){
            // Enemy is hurt
            // TODO! Change spritesheet for a more "damaged" one
        }

    }

    private void playDeathSound() {
        switch(this.type){
            case WEAK:
                Game.getInstance().audioPlayer.playEffect(AudioPlayer.WEAK_DEATH_SFX);
                break;
            case FAST:
                Game.getInstance().audioPlayer.playEffect(AudioPlayer.FAST_DEATH_SFX);
                break;
            case TANK:
                Game.getInstance().audioPlayer.playEffect(AudioPlayer.TANK_DEATH_SFX);
                break;
            case POLYVALENT:
                Game.getInstance().audioPlayer.playEffect(AudioPlayer.POLYVALENT_DEATH_SFX);
                break;
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
    	return getHealth() <= 0;
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

    public void setType(EnemyType type) {
        this.type = type;
    }

    public EnemyType getType() {
        return this.type;
    }

    public void setCanHurtPlayer(boolean canHurtPlayer) {
    	this.canHurtPlayer = canHurtPlayer;
    }
}
