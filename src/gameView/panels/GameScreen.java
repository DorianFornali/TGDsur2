package gameView.panels;

import gameLogic.Game;
import gameLogic.Stage;
import gameView.ViewController;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private ViewController viewController;
    private Stage currentStage;

    // The height of the HUD is 15% of the screen
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
    }

    private void drawGameBoard(Graphics g) {
        int m = currentStage.mcols;
        int n = currentStage.nrows;
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Hauteur de la partie haute occupée (15% de la fenêtre)
        int topHeight = (int) (panelHeight * 0.15);

        // Hauteur de l'espace restant pour le quadrillage
        int gridHeight = panelHeight - topHeight;

        // Largeur et hauteur d'une cellule du quadrillage
        int cellWidth = panelWidth / m;
        int cellHeight = gridHeight / n;

        // Dessiner le quadrillage
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
