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

    public TowerDragButton(int x, int y, int width, int height, ViewController vc, TowerType type){
        this.backgroundX = x;
        this.backgroundY = y;
        this.buttonX = x + width/8;
        this.buttonY = y + height/8;
        this.backgroundWidth = width;
        this.backgroundHeight= height;
        this.buttonWidth = (int) (width*0.6);
        this.buttonHeight = (int) (height*0.6);
        this.background = AssetManager.getInstance().getSprite("fastenButton1");
        this.vc = vc;
        initButton();
    }

    public JButton getDragButton(){
        return dragButton;
    }

    public BufferedImage getBackground(){
        return background;
    }

    public void initButton(){
        dragButton = new JButton();
        dragButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        dragButton.setFocusable(false);
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
                vc.generateEvent("TOWER_PLACEMENT", finalLocation);
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
