package gameView.panels;

import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                viewController.setCurrentPanel(new GameScreen(viewController));
            }
        });
        add(level1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
    }

}
