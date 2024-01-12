package gameLogic.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TowerFactory {

    // Tower that recover money
    public Tower createDefensiveTower(int row, int colunm) throws IOException {
        Tower tower = new Tower();

        System.out.println("Creating Defensive tower");
        tower.setHealth(200);
        tower.setMaxHealth(200);
        tower.setDamage(0);
        tower.setFiringRate(0);
        tower.setCanShoot(false);
        tower.setCanBlock(true);



        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("assets/sprites/towers/defensive.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        tower.setSpriteSheet(img);
        tower.setSpriteIndex(0);
        tower.setNSPRITES(1);








        return tower;
    }
}
