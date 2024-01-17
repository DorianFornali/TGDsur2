package gameView.panels;

import gameView.AssetManager;
import gameView.ViewController;
import gameView.customButtons.TowerDragButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class MainScreen extends JPanel {
    private ViewController viewController;
    JButton playButton;

    // This map contains the sprites of the towers and their description to be displayed in the main menu
    Map<BufferedImage, JLabel> towerSprites;
    public MainScreen(ViewController viewController) {
        this.viewController = viewController;
        setLayout(null);
        initPlayButton();
        initTowersSprites(); // For simple tower description
        // We alert the Game that the music should be played
        viewController.generateEvent("MAIN_MUSIC_PLAY", null);
    }

    private void initTowersSprites() {
        towerSprites = new LinkedHashMap<>();
        JLabel label = new JLabel("<html><i><b>This tower generates money over time," +
                " it should be the keystone of your strategy</html>");
        towerSprites.put(AssetManager.getInstance().getSprite("moneyTowerIcon"), label);
        add(label);

        label = new JLabel("<html><i><b>Basic tower, shoots projectiles in a straight line</html>");
        towerSprites.put(AssetManager.getInstance().getSprite("attackTowerIcon"), label);
        add(label);

        label = new JLabel("<html><i><b>Purely defensive tower, it doesn't do anything, except being a wall that the enemies must destroy</html>");
        towerSprites.put(AssetManager.getInstance().getSprite("defensiveTowerIcon"), label);
        add(label);

        label = new JLabel("<html><i><b>A powerful tower that covers three lanes at once, high strategic value</html>");
        towerSprites.put(AssetManager.getInstance().getSprite("multiTowerIcon"), label);
        add(label);

        label = new JLabel("<html><i><b>Emits a powerful wave that damages all enemies on screen. Very expensive</html>");
        towerSprites.put(AssetManager.getInstance().getSprite("globalTowerIcon"), label);
        add(label);

    }


    /** Initializes the play button */
    private void initPlayButton() {
        playButton = new JButton("Play");
        //button.setFocusable(false);
        playButton.setBounds((int) (ViewController.WIDTH*0.70f), (int) (ViewController.HEIGHT*0.35f),
                ViewController.WIDTH/5, ViewController.HEIGHT/5);
        playButton.setFont(new Font("Arial", Font.PLAIN, 20));
        playButton.setVerticalAlignment(SwingConstants.CENTER);
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Changing screen");
                viewController.setCurrentPanel(new LevelSelectionScreen(viewController));
            }
        });
        add(playButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        displayTowerInfo(g);
    }


    /** This function is responsible for displaying global information on the towers in the main menu */
    private void displayTowerInfo(Graphics g) {
        // First we display a plain background
        g.setColor(Color.LIGHT_GRAY);
        int backgroundX = (int) (ViewController.WIDTH*0.1f), backgroundY = (int) (ViewController.HEIGHT*0.1f);
        int backgroundWidth = (int) (ViewController.WIDTH*0.5f), backgroundHeight = (int) (ViewController.HEIGHT*0.8f);
        g.fillRect(backgroundX, backgroundY, backgroundWidth, backgroundHeight);


        int xOffset = (int) (backgroundX/2 + (ViewController.WIDTH*0.1f));
        int yOffset = (int) (backgroundY/2+ (ViewController.HEIGHT*0.15f));

        int imgWidth = (int) (ViewController.WIDTH*0.1f);
        int imgHeight = (int) (ViewController.HEIGHT*0.1f);

        g.setColor(Color.BLACK);
        for(Map.Entry<BufferedImage, JLabel> e : towerSprites.entrySet()){
            g.drawImage(e.getKey(), xOffset, yOffset, imgWidth, imgHeight, null);
            e.getValue().setBounds(xOffset + imgWidth, yOffset, imgWidth*3, imgHeight);

            // We created a dedicated JLabel for the description, we just have to display it
            yOffset += (int) (imgHeight*1.2f);
        }
    }

    public void displayPauseMenu() {
        // We display a small menu using a dialog object from Swing
        String[] options = {"Resume", "Quit"};
        int choice = JOptionPane.showOptionDialog(this, "Do you want to quit ?", "Pause", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice == 1){
            // We quit the game
            System.exit(0);
        }
    }
}
