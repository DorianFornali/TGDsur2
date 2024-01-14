package gameLogic.entity;

import gameView.AssetManager;
import gameView.ViewController;
import gameView.panels.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Projectile extends Entity{

    public boolean inTheWindow;

    public Projectile(){
        initSpriteSheet();
        setSpriteSheet(spriteSheet);
        setNSPRITES(1);
        setSpriteIndex(0);
        this.inTheWindow = true;
    }

    /** Initializes the spritesheet */
    private void initSpriteSheet() {
        spriteSheet = AssetManager.getInstance().getSprite("projectile");
    }

    public void update(){
        super.update();
        setX(getX() + getSpeed());

        if(hitbox.x > ViewController.WIDTH)
            inTheWindow = false;
    }

    public boolean toRemove(){
        return !inTheWindow;
    }

}
