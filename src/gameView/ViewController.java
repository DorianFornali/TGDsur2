package gameView;

import gameLogic.Game;
import gameView.panels.TestPanel;
import inputs.InputController;

import javax.swing.*;

public class ViewController extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private Game game;
    private JPanel currentPanel;

    private InputController inputController;

    public ViewController(Game game) {
        this.game = game;

        // Game's window settings
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("TGD/2");
        setResizable(false);
        setLocationRelativeTo(null);

        initUI();

        setVisible(true);
    }

    private void initUI() {
        // Graphical components initialization
    }

    public void renderGraphics() {
        if(this.currentPanel != null)
            this.currentPanel.repaint();
    }

    public void setInputController(InputController inputController) {
        this.inputController = inputController;
    }

    public void setCurrentPanel(JPanel panel){
        if(this.currentPanel != null){
            remove(this.currentPanel);
            currentPanel.invalidate();
        }
        panel.addMouseListener(this.inputController.getMouseController());
        panel.addKeyListener(this.inputController.getKeyboardController());

        panel.setFocusable(true);
        panel.requestFocus();

        this.currentPanel = panel;
        add(this.currentPanel);
        panel.validate();


    }

}
