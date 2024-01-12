package gameView;

import gameLogic.Game;
import gameLogic.entity.Enemy;
import gameLogic.entity.Entity;
import inputs.InputController;
import observerPattern.GameEvent;
import observerPattern.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for the game's window and its components.
 */
public class ViewController extends JFrame implements Observer {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private JPanel currentPanel;

    private InputController inputController;


    public ViewController() {
        // Game's window settings and initialization
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("TGD/2");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }



    /**
     * Renders graphics on the screen.
     */
    public void renderGraphics() {
        if(this.currentPanel != null) {
            this.currentPanel.repaint();
        }
    }



    public InputController getInputController(){
        return inputController;
    }
    public void setInputController(InputController inputController) {
        this.inputController = inputController;
    }

    /**
     * Changes the current panel to the one passed as parameter while removing the current one.
     * @param panel The panel to be set as the new one.
     */
    public void setCurrentPanel(JPanel panel){
        if(this.currentPanel != null){
            // We remove the current panel from the frame
            remove(this.currentPanel);
            currentPanel.invalidate();
            currentPanel.removeAll();
        }

        // We bind the controller, requestFocus and add the panel to the frame
        panel.addMouseListener(this.inputController.getMouseController());
        panel.addKeyListener(this.inputController.getKeyboardController());
        panel.setFocusable(true);
        this.currentPanel = panel;
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(this.currentPanel);
        pack(); // So the JFrame has the same size as its children panels
        panel.revalidate();
        panel.requestFocusInWindow(); // Needed to keep receiving keyevents
    }

    @Override
    public void receiveEventNotification(GameEvent event) {
        String typeEvent = event.getEventType();
        switch(typeEvent) {
            case "TEST_EVENT":
                System.out.println("Test event received by ViewController");
                break;
            case "PAUSE":
                pauseGame();
                break;
            default:
                break;
        }
    }


    private void pauseGame(){
        // No matter on which panel we are, escape will always pause the game and display a small menu
        Game.PAUSED = !Game.PAUSED;
    }

}
