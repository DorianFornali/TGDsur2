package gameView.panels;

import gameLogic.Game;
import gameLogic.Stage;
import gameLogic.StageNumero;
import gameLogic.entity.Enemy;
import gameLogic.entity.GlobalWave;
import gameLogic.entity.MoneyCoin;
import gameLogic.entity.Projectile;
import gameLogic.entity.Tower;
import gameLogic.entity.TowerType;
import gameView.AssetManager;
import gameView.ViewController;
import gameView.customButtons.TowerDragButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;


public class GameScreen extends JPanel {
    private ViewController viewController;
    private Stage currentStage;
    private int cellHeight, cellWidth;

    private JButton fastenButton;
    private ImageIcon[] fastenButtonSprites;

    private JLabel playerMoneyLabel;
    private BufferedImage healthIcon;

    private List<TowerDragButton> towerButtons;

    // Simple coordinates of the money JLabel for the coin animation travel
    public static int moneyLocationX, moneyLocationY;

    public GameScreen(ViewController viewController, StageNumero stageNumero) throws IOException {
        setPreferredSize(new Dimension(ViewController.WIDTH, ViewController.HEIGHT));
        this.viewController = viewController;
        this.currentStage = new Stage(stageNumero, viewController);
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
        drawTowerButton(g);
        renderEntities(g);
        updateButtonIcon();
        updatePlayerMoneyLabel();
        drawHealth(g);
    }

    private void drawHealth(Graphics g) {
        int health = currentStage.playerHealth;
        BufferedImage toDraw = healthIcon.getSubimage(0, (5 - health) * healthIcon.getHeight() / 6, healthIcon.getWidth(), healthIcon.getHeight() / 6);
        int drawingWidth = (int) (ViewController.WIDTH * 0.15);
        int drawingHeight = (int) (playerMoneyLabel.getHeight() * 1.5);
        int drawingX = (int) (playerMoneyLabel.getX() * 0.95f);
        int drawingY = (int) (playerMoneyLabel.getY() + drawingHeight * 0.6);

        g.drawImage(toDraw, drawingX, drawingY, drawingWidth, drawingHeight, null);
    }

    private void updatePlayerMoneyLabel() {
        int money = currentStage.getPlayerMoney();
        playerMoneyLabel.setText("Money: " + money);
    }

    private void drawBackground(Graphics g) {
        // For the moment a simple gray background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, ViewController.WIDTH, ViewController.HEIGHT);
    }

    private void drawTowerButton(Graphics g) {
        for (TowerDragButton t : towerButtons) {
            g.drawImage(t.getBackground(), t.getBackgroundX(), t.getBackgroundY(), t.getBackgroundWidth(), t.getBackgroundHeight(), null);
        }
    }


    /**
     * Render all entities
     */
    private void renderEntities(Graphics g) {
        renderTowers(g);
        renderEnemies(g);
        renderProjectiles(g);
    }

    private void renderProjectiles(Graphics g) {
        try {
            for (Projectile p : currentStage.projectilesAlive) {
                if (p != null && p.inTheWindow) {
                    BufferedImage img = p.getSprite();
                    Rectangle hitbox = p.getHitbox();
                    int drawingX = (int) hitbox.getX();
                    int drawingY = (int) hitbox.getY();
                    g.drawImage(img, drawingX, drawingY, hitbox.width, hitbox.height, null);

                /*
                g.setColor(Color.RED);
                g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

                g.setColor(Color.BLUE);
                g.drawRect(drawingX, drawingY, hitbox.width, hitbox.height);

                 */
                }
            }
        }catch(ConcurrentModificationException e){
            // This happpens when an enemy dies and is removed from the list while the viewcontroller iterates over the list
            // This error is NOT critical
        }
    }

    private void renderTowers(Graphics g) {
        for (Tower[] rowOfTower : currentStage.gameBoard) {
            for (Tower tower : rowOfTower) {
                if (tower != null && tower.isAlive()) {
                    BufferedImage img = tower.getSprite();
                    int drawingX = (int) tower.getX();
                    int drawingY = (int) tower.getY();
                    g.drawImage(img, drawingX, drawingY, cellWidth, cellHeight, null);

                    /*
                    g.setColor(Color.RED);
                    Rectangle hitbox = tower.getHitbox();
                    g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

                    g.setColor(Color.BLUE);
                    g.drawRect((int) tower.getX(), (int) tower.getY(), cellWidth, cellHeight);

                     */
                    // Additionally we want to take care of isolated cases such as the Global
                    // that needs another rendering step: its linked attack (the wave)
                    switch (tower.getTowerType()) {
                        case GLOBAL:
                            drawGlobalWave(tower.getGlobalWave(), g, drawingX + cellWidth / 2, drawingY + cellHeight / 2);
                            break;
                        case MONEY:
                            drawMoneyIcon(tower, g);
                            break;
                    }

                }
            }
        }
    }


    private void renderEnemies(Graphics g) {
        try {
            for (Enemy enemy : currentStage.enemiesAlive) {
                if (enemy != null && enemy.isUsed) {
                    BufferedImage img = enemy.getSprite();
                    int drawingX = (int) enemy.getX();
                    int drawingY = (int) enemy.getY();
                    g.drawImage(img, drawingX, drawingY, cellWidth, cellHeight, null);

                    /*
                    g.setColor(Color.RED);
                    Rectangle hitbox = enemy.getHitbox();
                    g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

                    g.setColor(Color.BLUE);
                    g.drawRect((int) enemy.getX(), (int) enemy.getY(), cellWidth, cellHeight);

                     */
                }
            }
        } catch (ConcurrentModificationException e){
            // This happpens when an enemy dies and is removed from the list while the viewcontroller iterates over the list
            // This error is NOT critical
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
        for (int i = 0; i < m - 1; i++) {
            int x = i * cellWidth;
            g.drawRect(x, topHeight, cellWidth, gridHeight);
        }
        for (int j = 0; j < n - 1; j++) {
            int y = topHeight + j * cellHeight;
            g.drawRect(0, y, panelWidth, cellHeight);
        }
    }

    private void drawTowerHUD(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), (int) (0.15 * getHeight()));
    }

    public void displayPauseMenu() {
        // We display a small menu using a dialog object from Swing
        String[] options = {"Resume", "Back to main menu"};
        int choice = JOptionPane.showOptionDialog(this, "Game paused", "Pause", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            // We quit the game
            viewController.setCurrentPanel(new MainScreen(viewController));
            Game.IN_GAME = false;
        }
        Game.PAUSED = !Game.PAUSED;

    }

    private void initUI() {
        // Initializing the fasten button
        fastenButtonSprites = new ImageIcon[]{
                new ImageIcon(AssetManager.getInstance().getSprite("fastenButton1").getScaledInstance(ViewController.WIDTH / 20, ViewController.WIDTH / 20, Image.SCALE_DEFAULT)),
                new ImageIcon(AssetManager.getInstance().getSprite("fastenButton2").getScaledInstance(ViewController.WIDTH / 20, ViewController.WIDTH / 20, Image.SCALE_DEFAULT)),
                new ImageIcon(AssetManager.getInstance().getSprite("fastenButton3").getScaledInstance(ViewController.WIDTH / 20, ViewController.WIDTH / 20, Image.SCALE_DEFAULT))
        };

        fastenButton = new JButton(fastenButtonSprites[0]);

        int buttonSize = ViewController.WIDTH / 20;
        fastenButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        fastenButton.setBounds((int) (ViewController.WIDTH * 0.9), buttonSize / 2, buttonSize, buttonSize);
        fastenButton.setVisible(true);
        fastenButton.setFocusable(false);
        fastenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.generateEvent("FASTEN", null);
            }
        });
        add(fastenButton);

        // Intializing the player money label
        playerMoneyLabel = new JLabel("TEST");
        int labelSize = ViewController.WIDTH / 10;
        playerMoneyLabel.setPreferredSize(new Dimension(labelSize, labelSize / 4));
        playerMoneyLabel.setForeground(Color.BLACK);
        playerMoneyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerMoneyLabel.setBounds((int) (ViewController.WIDTH * 0.55), (int) (ViewController.HEIGHT * 0.06), labelSize * 2, labelSize / 4);
        moneyLocationX = (int) (ViewController.WIDTH * 0.55);
        moneyLocationY = (int) (ViewController.HEIGHT * 0.06);
        playerMoneyLabel.setVisible(true);
        add(playerMoneyLabel);

        initTowerButtons();
        initInfoLabels();
        initHealthIcon();
    }

    private void initHealthIcon() {
        healthIcon = AssetManager.getInstance().getSprite("healthIcon");
    }

    private void initInfoLabels() {
        JLabel tLabel = new JLabel("Press T to speed up the game");
        int labelSize = ViewController.WIDTH / 10;
        tLabel.setPreferredSize(new Dimension(labelSize, labelSize / 4));
        tLabel.setForeground(Color.DARK_GRAY);
        tLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        tLabel.setBounds(ViewController.WIDTH / 2 + labelSize * 2, (int) (ViewController.HEIGHT * 0.08), labelSize * 2, labelSize / 4);
        tLabel.setVisible(true);
        add(tLabel);

        JLabel escLabel = new JLabel("Press Esc to pause the game");
        escLabel.setPreferredSize(new Dimension(labelSize, labelSize / 4));
        escLabel.setForeground(Color.DARK_GRAY);
        escLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        escLabel.setBounds(ViewController.WIDTH / 2 + labelSize * 2, (int) (ViewController.HEIGHT * 0.04), labelSize * 2, labelSize / 4);
        escLabel.setVisible(true);
        add(escLabel);
    }

    private void initTowerButtons() {
        this.towerButtons = new ArrayList<>();
        int squareSize = ViewController.WIDTH / 15;
        int offset = (int) (ViewController.WIDTH * 0.01f);
        for (TowerType t : TowerType.values()) {
            TowerDragButton towerDragButton = new TowerDragButton(offset, (int) ((ViewController.HEIGHT) * 0.05f),
                    squareSize, squareSize, viewController, t);
            towerButtons.add(towerDragButton);
            add(towerDragButton.getDragButton());
            offset += ViewController.WIDTH / 10;
            add(towerDragButton.getTowerPriceLabel());
        }

    }

    private void updateButtonIcon() {
        fastenButton.setIcon(fastenButtonSprites[Game.CURRENT_SPEED_FACTOR - 1]);
        fastenButton.setVisible(!Game.PAUSED);
    }

    /**
     * Fetch information on the gameboard to determine the height of a game cell, to calculate the hitbox's size
     */
    public static int calculateCellHeight() {
        int n = Stage.nrows;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int gridHeight = panelHeight - topHeight;
        int cellHeight = gridHeight / n;
        return cellHeight;
    }

    /**
     * Fetch information on the gameboard to determine the width of a game cell, to calculate the hitbox's size
     */
    public static int calculateCellWidth() {
        int m = Stage.mcols;
        int panelWidth = ViewController.WIDTH;
        int panelHeight = ViewController.HEIGHT;
        int topHeight = (int) (panelHeight * 0.15);
        int cellWidth = panelWidth / m;
        return cellWidth;
    }

    /**
     * Converts a row to coordinates
     */
    public static float rowToY(int row) {
        //return (float) (ViewController.HEIGHT*0.15 + row * ((ViewController.HEIGHT-ViewController.HEIGHT*0.15)/Stage.nrows));
        return (float) (ViewController.HEIGHT * 0.15 + row * ((ViewController.HEIGHT - ViewController.HEIGHT * 0.15) / Stage.nrows));
    }

    public static float columnToX(int column) {
        return (float) (column * ((ViewController.WIDTH) / Stage.mcols));
    }


    /**
     * Draws a circle with center the tower that shoots,
     * this circle will quickly get bigger, then disappear once out of bounds
     */
    private void drawGlobalWave(GlobalWave globalWaveToDraw, Graphics g, int centerX, int centerY) {
        if (globalWaveToDraw == null) return;
        g.setColor(Color.RED);
        int r = globalWaveToDraw.getRadius();
        g.drawOval(centerX - r, centerY - r, 2 * r, 2 * r);
    }

    private void drawMoneyIcon(Tower tower, Graphics g) {
        MoneyCoin mc = tower.getMoneyCoin();
        if (mc == null || mc.dead) return;
        BufferedImage toDraw = mc.getSprite();

        g.drawImage(toDraw, (int) mc.getX(), (int) mc.getY(), cellWidth / 2, cellHeight / 2, null);

    }
}
