package gameView.panels;

import gameLogic.StageNumero;
import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LevelSelectionScreen extends JPanel {
    private ViewController viewController;

    public LevelSelectionScreen(ViewController viewController) {
        this.viewController = viewController;
        initButtons();
        setLayout(null);

    }

    private void initButtons() {
        int offset = 0;
        int buttonWidth = ViewController.WIDTH / 5;
        int buttonHeight = ViewController.HEIGHT / 5;

        JButton level1 = new JButton();
        level1.setBounds((int) (buttonWidth * 0.8 + offset), ViewController.HEIGHT / 2 - buttonHeight, buttonWidth, buttonHeight);
        offset += level1.getWidth();
        level1.setFont(new Font("Arial", Font.PLAIN, 20));
        level1.setVerticalAlignment(SwingConstants.CENTER);
        level1.setHorizontalAlignment(SwingConstants.CENTER);
        level1.setText("STAGE 1");
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

        JButton level2 = new JButton();
        level2.setBounds((int) (buttonWidth * 0.8 + offset * 1.2f), ViewController.HEIGHT / 2 - buttonHeight, buttonWidth, buttonHeight);
        offset += level2.getWidth();
        level2.setFont(new Font("Arial", Font.PLAIN, 20));
        level2.setVerticalAlignment(SwingConstants.CENTER);
        level2.setHorizontalAlignment(SwingConstants.CENTER);
        level2.setText("STAGE 2");
        level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Changing screen");
                try {
                    viewController.setCurrentPanel(new GameScreen(viewController, StageNumero.STAGE2));
                    viewController.generateEvent("STAGE2_MUSIC_PLAY", null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(level2);

        JButton level3 = new JButton();
        level3.setBounds((int) (buttonWidth * 0.8 + offset * 1.2f), ViewController.HEIGHT / 2 - buttonHeight, buttonWidth, buttonHeight);
        level3.setFont(new Font("Arial", Font.PLAIN, 20));
        level3.setVerticalAlignment(SwingConstants.CENTER);
        level3.setHorizontalAlignment(SwingConstants.CENTER);
        level3.setText("STAGE 3");
        level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Changing screen");
                try {
                    viewController.setCurrentPanel(new GameScreen(viewController, StageNumero.STAGE3));
                    viewController.generateEvent("STAGE3_MUSIC_PLAY", null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(level3);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
    }

    public void displayPauseMenu() {
        // We display a small menu using a dialog object from Swing
        String[] options = {"No", "Yes"};
        int choice = JOptionPane.showOptionDialog(this, "Back to main screen ?", "Esc", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            // We quit the game
            viewController.setCurrentPanel(new MainScreen(viewController));
        }
    }
}
