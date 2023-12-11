package gameView.panels;

import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPanel extends JPanel {
    private ViewController viewController;
    private final boolean showRect;
    public TestPanel(ViewController viewController, boolean showRect) {
        this.viewController = viewController;
        this.showRect = showRect;

        JButton button = new JButton("Click me");
        //button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.setCurrentPanel(new TestPanel(viewController, !showRect));
                System.out.println("Changing screen");
            }
        });
        button.setBounds(ViewController.WIDTH / 2 - 100, ViewController.HEIGHT / 2, 100, 100);
        add(button);

    }

    public void paintComponent(Graphics g) {
        // Use Example
        super.paintComponent(g);
        g.setColor(Color.RED);
        if(showRect) {
            g.fillRect(ViewController.WIDTH / 2 - 100, ViewController.HEIGHT / 2 - 100, 100, 100);
        }
    }

}
