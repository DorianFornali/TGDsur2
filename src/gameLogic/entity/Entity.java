package gameLogic.entity;

import gameLogic.Game;
import gameView.panels.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    // Entity coordinates
    private float x, y;

    // Each entity is placed in a row and cannot change (Exceptions apart).
    // An enemy at row 1 can only move in this row.
    private int entityRow;

    protected BufferedImage spriteSheet;
    protected BufferedImage currentSprite;

    // The amount of sprites in the spritesheet
    protected int NSPRITES;

    // The index of the current sprite in the spritesheet
    protected int spriteIndex;

    // Used to time the animation of the sprite
    protected double previousTimer;

    // The actual hitbox of the entity, collision will be checked against this
    protected Rectangle hitbox;

    protected int damage;

    // Only for towers and enemies, not projectiles
    protected float firingRate;
    protected double previousFiring; // Last system time the entity shot

    // Only for enemies and towers, not projectiles
    protected int maxHealth, health;
    private float speed; // Only for enemies and projectiles

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public int getRow(){
        return entityRow;
    }

    public void setEntityRow(int row){
        this.entityRow = row;
    }

    public BufferedImage getCurrentSprite(){
        return this.currentSprite;
    }


    public void update() {
        updateSprite();
        updateHitbox();
    }


    private void updateHitbox() {
        int hitboxWidth = (int) hitbox.getWidth(), hitboxHeight = (int) hitbox.getHeight();
        // We relocate the hitbox rectangle position to the entity's position
        int newX = (int) (getX() + GameScreen.calculateCellWidth()/2 - hitboxWidth/2);
        int newY = (int) getY() + hitboxHeight/2;
        hitbox.setLocation(newX, newY);
    }

    private void updateSprite() {
        double now = System.nanoTime();
        if(now - previousTimer > 1000000000/10){
            previousTimer = now;
            spriteIndex++;
            if(spriteIndex >= NSPRITES){
                spriteIndex = 0;
            }
            int spriteHeight = spriteSheet.getHeight() / NSPRITES;
            currentSprite = spriteSheet.getSubimage(0, spriteIndex * spriteHeight, spriteHeight, spriteHeight);
        }
    }

    public BufferedImage getSprite() {
        return currentSprite;
    }


    public void setSpriteSheet(BufferedImage spriteSheet){
        this.spriteSheet = spriteSheet;
    }

    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }

    public void setNSPRITES(int NSPRITES) {
        this.NSPRITES = NSPRITES;
    }

    public Rectangle getHitbox() {
    	return hitbox;
    }

    protected void setHitbox(int x, int y, int width, int height){
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void setFiringRate(int firingRate){
        // We convert the firing rate from shots per second to nanoseconds
        this.firingRate = firingRate*1000000000f;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getDamage() {
    	return damage;
    }

    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }


    public void setHealth(int health){
        this.health = health;
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
    	return speed;
    }
}
