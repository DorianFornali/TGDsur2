package gameView.panels;

import gameLogic.Game;
import gameLogic.Stage;
import gameLogic.entity.Enemy;
import gameLogic.entity.Entity;
import gameView.ViewController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class GameScreen extends JPanel {
    private ViewController viewController;
    private Stage currentStage;

    private int cellHeight, cellWidth;
    public GameScreen(ViewController viewController){
        this.viewController = viewController;
        this.currentStage = new Stage();
        Game.getInstance().setCurrentStage(currentStage);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, ViewController.WIDTH, ViewController.HEIGHT);
        g.setColor(Color.GRAY);
        drawTowerHUD(g);
        drawGameBoard(g);
        renderEntities(g);

    }


    private void renderEntities(Graphics g) {
        for(Entity e : Stage.entities){
            Enemy enemy = (Enemy) e;
            if(enemy != null && enemy.isUsed) {
                BufferedImage img = enemy.getSprite();
                g.drawImage(img, (int) (enemy.getX() - cellWidth / 2), (int) (enemy.getY() - cellHeight / 2), cellWidth, cellHeight, null);
            }
        }
    }

    private void drawGameBoard(Graphics g) {
        int m = Stage.mcols;
        int n = Stage.nrows;
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Height of the top HUD
        int topHeight = (int) (panelHeight * 0.15);

        int gridHeight = panelHeight - topHeight;

        cellWidth = panelWidth / m;
        cellHeight = gridHeight / n;

        g.setColor(Color.BLACK);
        for (int i = 0; i < m-1; i++) {
            int x = i * cellWidth;
            g.drawRect(x, topHeight, cellWidth, gridHeight);
        }
        for (int j = 0; j < n-1; j++) {
            int y = topHeight + j * cellHeight;
            g.drawRect(0, y, panelWidth, cellHeight);
        }
    }

    private void drawTowerHUD(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, ViewController.WIDTH, (int) (0.15*getHeight()));
    }
}
