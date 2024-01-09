package gameLogic.entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    // Entity coordinates
    private float x, y;

    // Each entity is placed in a row and cannot change (Exceptions apart).
    // An enemy at row 1 can only move in this row.
    private int entityRow;

    protected BufferedImage[] spriteSheet;
    protected BufferedImage currentSprite;

    protected int speed;




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
    }
}
