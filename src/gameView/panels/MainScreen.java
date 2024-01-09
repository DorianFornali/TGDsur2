package gameView.panels;

import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JPanel {
    private ViewController viewController;
    public MainScreen(ViewController viewController) {
        this.viewController = viewController;
        setLayout(null);
        JButton button = new JButton("Click me");
        //button.setFocusable(false);
        button.setBounds(ViewController.WIDTH/2 - 200/2, ViewController.HEIGHT/2 - 100, 200, 100);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setText("Level selection");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Changing screen");
                viewController.setCurrentPanel(new LevelSelectionScreen(viewController));
            }
        });
        add(button);

    }

    public void paintComponent(Graphics g) {
        // Use Example
        super.paintComponent(g);
        g.setColor(Color.RED);

    }

}
