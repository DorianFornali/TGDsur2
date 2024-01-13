package gameView.panels;

import gameLogic.Game;
import gameLogic.Stage;
import gameLogic.StageNumero;
import gameLogic.entity.Enemy;
import gameLogic.entity.Entity;
import gameLogic.entity.Tower;
import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class GameScreen extends JPanel {
    private ViewController viewController;
    private Stage currentStage;
    private int cellHeight, cellWidth;

    private JButton fastenButton;
    private ImageIcon[] fastenButtonSprites;

    public GameScreen(ViewController viewController) throws IOException {
        setPreferredSize(new Dimension(ViewController.WIDTH, ViewController.HEIGHT));
        this.viewController = viewController;
        this.currentStage = new Stage(StageNumero.STAGE1);
        Game.getInstance().setCurrentStage(currentStage);
        Game.IN_GAME = true;
        initUI();
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, ViewController.WIDTH, ViewController.HEIGHT);
        g.setColor(Color.GRAY);
        drawBackground(g);
        drawTowerHUD(g);
        drawGameBoard(g);
        renderEntities(g);
        updateButtonIcon();
    }

    private void drawBackground(Graphics g) {
        // For the moment a simple gray background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, ViewController.WIDTH, ViewController.HEIGHT);
    }


    /** Render all entities */
    private void renderEntities(Graphics g) {
        renderTowers(g);
        renderEnemies(g);
        renderProjectiles(g);
    }

    private void renderProjectiles(Graphics g) {
        //TODO! Create projectiles and either put them in entities array or in a dedicated projectiles list
    }

    private void renderTowers(Graphics g) {
        for(Tower[] rowOfTower : currentStage.gameBoard) {
            for (Tower tower : rowOfTower) {
                if (tower != null) {
                    BufferedImage img = tower.getSprite();
                    int drawingX = (int) tower.getX();
                    int drawingY = (int) tower.getY();
                    g.drawImage(img, drawingX, drawingY, cellWidth, cellHeight, null);

                    // Drawing the hitbox for debug purposes
                    g.setColor(Color.RED);
                    Rectangle hitbox = tower.getHitbox();
                    g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

                    // debug, drawing image delimitations
                    //g.setColor(Color.BLUE);
                    //g.drawRect((int) tower.getX(), (int) tower.getY(), cellWidth, cellHeight);
                }
            }
        }
    }

    private void renderEnemies(Graphics g) {
        for(Entity e : currentStage.entities){
            if (e instanceof Enemy) {
                Enemy enemy = (Enemy) e;
                if(enemy != null && enemy.isUsed) {
                    BufferedImage img = enemy.getSprite();
                    int drawingX = (int) enemy.getX();
                    int drawingY = (int) enemy.getY();
                    g.drawImage(img, drawingX, drawingY, cellWidth, cellHeight, null);

                    // Drawing the hitbox for debug purposes
                    g.setColor(Color.RED);
                    Rectangle hitbox = enemy.getHitbox();
                    g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

                    // debug, drawing image delimitations
                    //g.setColor(Color.BLUE);
                    //g.drawRect((int) enemy.getX(), (int) enemy.getY(), cellWidth, cellHeight);
                }
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
        g.fillRect(0, 0, getWidth(), (int) (0.15*getHeight()));
    }

    public void displayPauseMenu() {
        // We display a small menu using a dialog object from Swing
        String[] options = {"Resume", "Back to main menu"};
        int choice = JOptionPane.showOptionDialog(this, "Game paused", "Pause", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice == 1){
            // We quit the game
            viewController.setCurrentPanel(new MainScreen(viewController));
            Game.IN_GAME = false;
        }
        Game.PAUSED = !Game.PAUSED;

    }

    private void initUI() {
        fastenButtonSprites = new ImageIcon[]{
                new ImageIcon(new ImageIcon("assets/sprites/ui/fastenButton1.PNG").getImage().getScaledInstance(ViewController.WIDTH/20, ViewController.WIDTH/20, Image.SCALE_DEFAULT)),
                new ImageIcon(new ImageIcon("assets/sprites/ui/fastenButton2.PNG").getImage().getScaledInstance(ViewController.WIDTH/20, ViewController.WIDTH/20, Image.SCALE_DEFAULT)),
                new ImageIcon(new ImageIcon("assets/sprites/ui/fastenButton3.PNG").getImage().getScaledInstance(ViewController.WIDTH/20, ViewController.WIDTH/20, Image.SCALE_DEFAULT))
        };

        fastenButton = new JButton(fastenButtonSprites[0]);

        int buttonSize = ViewController.WIDTH/20;
        fastenButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        fastenButton.setBounds((int) (ViewController.WIDTH*0.9), buttonSize/2, buttonSize, buttonSize);
        fastenButton.setVisible(true);
        fastenButton.setFocusable(false);
        fastenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.generateEvent("FASTEN", null);
            }
        });
        add(fastenButton);
    }

    private void updateButtonIcon() {
        fastenButton.setIcon(fastenButtonSprites[Game.CURRENT_SPEED_FACTOR-1]);
        fastenButton.setVisible(!Game.PAUSED);
    }

    /** Fetch information on the gameboard to determine the height of a game cell, to calculate the hitbox's size*/
    public static int calculateCellHeight() {
        int n = Stage.nrows;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int gridHeight = panelHeight - topHeight;
        int cellHeight = gridHeight / n;
        return cellHeight;
    }

    /** Fetch information on the gameboard to determine the width of a game cell, to calculate the hitbox's size*/
    public static int calculateCellWidth() {
        int m = Stage.mcols;
        int panelWidth = ViewController.WIDTH;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int cellWidth = panelWidth / m;
        return cellWidth;
    }

    /** Converts a row to coordinates */
    public static float rowToY(int row){
        //return (float) (ViewController.HEIGHT*0.15 + row * ((ViewController.HEIGHT-ViewController.HEIGHT*0.15)/Stage.nrows));
        return (float) (ViewController.HEIGHT*0.15 + row * ((ViewController.HEIGHT-ViewController.HEIGHT*0.15)/Stage.nrows));
    }

    public static float columnToX(int column){
        return (float) (column * ((ViewController.WIDTH)/Stage.mcols));
    }

}
