package gameLogic.entity;

import gameLogic.Stage;
import gameView.ViewController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TowerFactory {

    // Tower that tank (coconut in PVZ)
    public static Tower createDefensiveTower(int row, int colunm) {
        Tower tower = new Tower();

        System.out.println("Creating Defensive tower");
        tower.setHealth(400);
        tower.setMaxHealth(200);
        tower.setDamage(0);
        tower.setFiringRate(0);
        tower.setCanShoot(false);
        tower.setCanBlock(true);
        tower.setPrice(100); //same as an attack tower

        // We set the X coordinate of the tower to the center of the entity's colunm
        colunm = colunm % Stage.mcols;
        tower.setX(colunmToX(colunm));

        // We set the Y coordinate of the tower to the center of the entity's row
        row = row % Stage.nrows;
        tower.setY(rowToY(row));


        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("assets/sprites/Tower/defensive.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        tower.setSpriteSheet(img);
        tower.setSpriteIndex(0);
        tower.setNSPRITES(1);

        int cellWidth = calculateCellWidth();
        int cellHeight = calculateCellHeight();

        int hitboxWidth = (int)(cellWidth*0.8), hitboxHeight = (int)(cellHeight*0.8);
        tower.setHitbox(0, 0, hitboxWidth, hitboxHeight);

        return tower;
    }

    // Tower that recover money (sunflower in PVZ)
    public static Tower createRecoveryTower(int row, int colunm) {
        Tower tower = new Tower();

        System.out.println("Creating Recovery tower");
        tower.setHealth(50);
        tower.setMaxHealth(50);
        tower.setDamage(25); //the damage is the amount of money recovered
        tower.setFiringRate(20);
        tower.setCanShoot(true);
        tower.setCanBlock(false);
        tower.setPrice(50); //half of an attack tower

        // We set the X coordinate of the tower to the center of the entity's colunm
        colunm = colunm % Stage.mcols;
        tower.setX(colunmToX(colunm));

        // We set the Y coordinate of the tower to the center of the entity's row
        row = row % Stage.nrows;
        tower.setY(rowToY(row));

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("assets/sprites/Tower/recovery.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        tower.setSpriteSheet(img);
        tower.setSpriteIndex(0);
        tower.setNSPRITES(1);

        int cellWidth = calculateCellWidth();
        int cellHeight = calculateCellHeight();

        int hitboxWidth = (int)(cellWidth*0.8), hitboxHeight = (int)(cellHeight*0.8);
        tower.setHitbox(0, 0, hitboxWidth, hitboxHeight);

        return tower;
    }

    // Tower that attack (peashooter in PVZ)
    public static Tower createAttackTower(int row, int colunm) {
        Tower tower = new Tower();

        System.out.println("Creating Attack tower");
        tower.setHealth(100);
        tower.setMaxHealth(100);
        tower.setDamage(10);    // a solo peashooter should be able to kill a standard enemy while he move 4 tiles
        tower.setFiringRate(5);
        tower.setCanShoot(true);
        tower.setCanBlock(true);
        tower.setPrice(100); //Standard price

        // We set the X coordinate of the tower to the center of the entity's colunm
        colunm = colunm % Stage.mcols;
        tower.setX(colunmToX(colunm));

        // We set the Y coordinate of the tower to the center of the entity's row
        row = row % Stage.nrows;
        tower.setY(rowToY(row));

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("assets/sprites/Tower/attack.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        tower.setSpriteSheet(img);
        tower.setSpriteIndex(0);
        tower.setNSPRITES(8);

        int cellWidth = calculateCellWidth();
        int cellHeight = calculateCellHeight();

        int hitboxWidth = (int)(cellWidth*0.8), hitboxHeight = (int)(cellHeight*0.8);
        tower.setHitbox(0, 0, hitboxWidth, hitboxHeight);

        return tower;
    }

    // Tower that attack on tree line at the same time (triple shooter in PVZ)
    public static Tower createMultiTower(int row, int colunm){
        Tower tower = new Tower();

        System.out.println("Creating Multi tower");
        tower.setHealth(150);
        tower.setMaxHealth(150); //little bit more tanky
        tower.setDamage(10);    // same damage as an attack tower
        tower.setFiringRate(5);
        tower.setCanShoot(true);
        tower.setCanBlock(true);
        tower.setPrice(325); //Triple plus an extra

        // We set the X coordinate of the tower to the center of the entity's colunm
        colunm = colunm % Stage.mcols;
        tower.setX(colunmToX(colunm));

        // We set the Y coordinate of the tower to the center of the entity's row
        row = row % Stage.nrows;
        tower.setY(rowToY(row));

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("assets/sprites/Tower/multi.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        tower.setSpriteSheet(img);
        tower.setSpriteIndex(0);
        tower.setNSPRITES(1);

        int cellWidth = calculateCellWidth();
        int cellHeight = calculateCellHeight();

        int hitboxWidth = (int)(cellWidth*0.8), hitboxHeight = (int)(cellHeight*0.8);
        tower.setHitbox(0, 0, hitboxWidth, hitboxHeight);

        return tower;
    }

    //Tower that can attack every enemy on the map at the same time
    public static Tower createGlobalTower(int row, int colunm){
        Tower tower = new Tower();

        System.out.println("Creating Global tower");
        tower.setHealth(200);
        tower.setMaxHealth(20); //really squishy, must protect at all cost
        tower.setDamage(3);    // little damage to compensate the range
        tower.setFiringRate(5);
        tower.setCanShoot(true);
        tower.setCanBlock(true);
        tower.setPrice(500); //extremly expensive, late game tower

        // We set the X coordinate of the tower to the center of the entity's colunm
        colunm = colunm % Stage.mcols;
        tower.setX(colunmToX(colunm));

        // We set the Y coordinate of the tower to the center of the entity's row
        row = row % Stage.nrows;
        tower.setY(rowToY(row));

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("assets/sprites/Tower/global.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        tower.setSpriteSheet(img);
        tower.setSpriteIndex(0);
        tower.setNSPRITES(1);

        int cellWidth = calculateCellWidth();
        int cellHeight = calculateCellHeight();

        int hitboxWidth = (int)(cellWidth*0.8), hitboxHeight = (int)(cellHeight*0.8);
        tower.setHitbox(0, 0, hitboxWidth, hitboxHeight);

        return tower;
    }

    /** Fetch information on the gameboard to determine the height of a game cell, to calculate the hitbox's size*/
    private static int calculateCellHeight() {
        int n = Stage.nrows;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int gridHeight = panelHeight - topHeight;
        int cellHeight = gridHeight / n;
        return cellHeight;
    }

    /** Fetch information on the gameboard to determine the width of a game cell, to calculate the hitbox's size*/
    private static int calculateCellWidth() {
        int m = Stage.mcols;
        int panelWidth = ViewController.WIDTH;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int cellWidth = panelWidth / m;
        return cellWidth;
    }

    private static float rowToY(int row){
        return (float) (ViewController.HEIGHT*0.15 + (row-1) * ((ViewController.HEIGHT-ViewController.HEIGHT*0.15)/Stage.nrows));
    }

    private static float colunmToX(int colunm){
        return (float) (colunm * ((ViewController.WIDTH)/Stage.mcols));
    }
}
