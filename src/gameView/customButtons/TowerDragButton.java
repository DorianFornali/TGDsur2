package gameView.customButtons;

import gameLogic.entity.TowerType;
import gameView.AssetManager;
import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** This class represents the tower buttons in the game screen hud
 * Contains a JButton that is draggable and a background */
public class TowerDragButton {
    private int backgroundX, backgroundY;
    private int buttonX, buttonY;
    private int backgroundWidth, backgroundHeight;
    private int buttonWidth, buttonHeight;

    private ViewController vc;

    private JButton dragButton;
    private BufferedImage background;
    private TowerType type;

    public TowerDragButton(int x, int y, int width, int height, ViewController vc, TowerType type){
        this.backgroundX = x;
        this.backgroundY = y;
        this.buttonX = x + width/8;
        this.buttonY = y + height/8;
        this.backgroundWidth = width;
        this.backgroundHeight= height;
        this.buttonWidth = (int) (width*0.8);
        this.buttonHeight = (int) (height*0.8);
        this.background = AssetManager.getInstance().getSprite("tower_DragButton_background");
        this.vc = vc;
        this.type = type;
        initButton();
    }

    public JButton getDragButton(){
        return dragButton;
    }

    public BufferedImage getBackground(){
        return background;
    }

    public void initButton() {
        dragButton = new JButton();
        BufferedImage img = null;
        ImageIcon icon = null;

        switch (type) {
            case ATTACK:
                img = AssetManager.getInstance().getSprite("attackTowerIcon");
                icon = new ImageIcon(img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));
                break;

            case DEFENSIVE:
                img = AssetManager.getInstance().getSprite("defensiveTowerIcon");
                icon = new ImageIcon(img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));
                break;

            case MONEY:
                img = AssetManager.getInstance().getSprite("moneyTowerIcon");
                icon = new ImageIcon(img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));
                break;

            case MULTI:
                img = AssetManager.getInstance().getSprite("multiTowerIcon");
                icon = new ImageIcon(img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));
                break;

            case GLOBAL:
                img = AssetManager.getInstance().getSprite("globalTowerIcon");
                icon = new ImageIcon(img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));
                break;
        }

        dragButton.setOpaque(false);
        dragButton.setContentAreaFilled(false);
        dragButton.setBorderPainted(false);
        dragButton.setIcon(icon);
        dragButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        dragButton.setFocusable(false);
        dragButton.setLayout(null);
        associateMouseAdapter(dragButton);
    }

    /** Associates the button to a custom mouse adapter that will allow us to move the button itself
     * Additionally, associates an action listener to retrieve the final coordinates when button is released */
    private void associateMouseAdapter(JButton button){
        final Point offset = new Point();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offset.setLocation(e.getPoint());
            }
        });

        button.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point newLocation = button.getLocation();
                newLocation.translate(e.getX() - offset.x, e.getY() - offset.y);
                button.setLocation(newLocation);

            }

        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // We retrieve the final coordinates of the button
                // We will send a message to Stage to create a tower at these coordinates
                // And relocate the button to its original spot
                Point finalLocation = button.getLocation();

                button.setLocation(buttonX, buttonY);

                switch(type){
                    case ATTACK:
                        vc.generateEvent("TOWER_PLACEMENT_ATTACK", finalLocation);
                        break;
                    case DEFENSIVE:
                        vc.generateEvent("TOWER_PLACEMENT_DEFENSIVE", finalLocation);
                        break;
                    case MONEY:
                        vc.generateEvent("TOWER_PLACEMENT_MONEY", finalLocation);
                        break;
                    case MULTI:
                        vc.generateEvent("TOWER_PLACEMENT_MULTI", finalLocation);
                        break;
                    case GLOBAL:
                        vc.generateEvent("TOWER_PLACEMENT_GLOBAL", finalLocation);
                        break;
                }
            }
        });
    }

    public int getBackgroundX() {
        return backgroundX;
    }

    public int getBackgroundY() {
        return backgroundY;
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    public int getbuttonX() {
        return buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }
}
