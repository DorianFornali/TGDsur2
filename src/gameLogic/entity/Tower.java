package gameLogic.entity;

import gameLogic.Game;

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

    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public boolean toRemove(){
        return health <= 0;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void setFiringRate(int firingRate){
        this.firingRate = firingRate;
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

    public void setHitbox(int x, int y, int width, int height){
        this.hitbox = new Rectangle(x, y, width, height);
    }
}
