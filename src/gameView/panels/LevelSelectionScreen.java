package gameView.panels;

import gameLogic.StageNumero;
import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LevelSelectionScreen extends JPanel{
    private ViewController viewController;
    public LevelSelectionScreen(ViewController viewController) {
        this.viewController = viewController;
        setLayout(null);
        JButton level1 = new JButton();
        level1.setBounds(ViewController.WIDTH/2 - 200/2, ViewController.HEIGHT/2 - 100, 200, 100);
        level1.setFont(new Font("Arial", Font.PLAIN, 20));
        level1.setVerticalAlignment(SwingConstants.CENTER);
        level1.setHorizontalAlignment(SwingConstants.CENTER);
        level1.setText("LEVEL 1");
        level1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Changing screen");
                try {
                    viewController.setCurrentPanel(new GameScreen(viewController, StageNumero.STAGE1));
                    viewController.generateEvent("STAGE1_MUSIC_PLAY", null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(level1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
    }

    public void displayPauseMenu() {
        // We display a small menu using a dialog object from Swing
        String[] options = {"No", "Yes"};
        int choice = JOptionPane.showOptionDialog(this, "Back to main screen ?", "Esc", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice == 1){
            // We quit the game
            viewController.setCurrentPanel(new MainScreen(viewController));
        }
    }
}
