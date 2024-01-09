package gameLogic.entity;

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
    protected float spriteWidth, spriteHeight;


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

    public Image getSprite() {
        return currentSprite;
    }


    public void setSpriteSheet(BufferedImage spriteSheet){
        this.spriteSheet = spriteSheet;
    }

    public void setSpriteWidth(float width){
        this.spriteWidth = width;
    }

    public void setSpriteHeight(float height){
        this.spriteHeight = height;
    }

    public float getSpriteWidth(){
        return this.spriteWidth;
    }

    public float getSpriteHeight(){
        return this.spriteHeight;
    }

}
