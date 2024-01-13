package gameLogic.entity;

import gameView.ViewController;
import gameView.panels.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Projectile extends Entity{

    private static BufferedImage spriteSheet;
    private static final String spritePath = "assets/sprites/ui/fastenButton1.png";
    public boolean inTheWindow;

    public Projectile(){
        initSpriteSheet();
        setSpriteSheet(spriteSheet);
        setNSPRITES(1);
        setSpriteIndex(0);
        this.inTheWindow = true;
    }

    /** Initializes the spritesheet if necessary, the image shall be imported only the first time */
    private void initSpriteSheet() {
        if(spriteSheet == null){
            try{
                spriteSheet = ImageIO.read(new File(spritePath));
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }

    public void update(){
        super.update();
        setX(getX() + getSpeed());
    }

    public boolean toRemove(){
        return getX() > ViewController.WIDTH;
    }

}
