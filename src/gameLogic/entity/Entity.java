package gameLogic.entity;

public abstract class Entity {
    // Entity coordinates
    private float x, y;
    // TODO! Add the sprite/animation related components

    // Each entity is placed in a row and cannot change (Exceptions apart).
    // An enemy at row 1 can only move in this row.
    private int entityRow;

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

}
