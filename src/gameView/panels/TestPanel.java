package gameView.panels;

import gameView.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPanel extends JPanel {
    private ViewController viewController;
    private boolean showRect;
    public TestPanel(ViewController viewController, boolean showRect) {
        this.viewController = viewController;
        this.showRect = showRect;
    }

    public void paintComponent(Graphics g) {
        // Use Example
        g.setColor(Color.RED);
        if(showRect) {
            g.fillRect(ViewController.WIDTH / 2 - 100, ViewController.HEIGHT / 2 - 100, 100, 100);
        }

    }
}
