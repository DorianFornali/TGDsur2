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
    }

    public void paintComponent(Graphics g) {
        // Use Example
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawArc(ViewController.WIDTH / 2 - 100, ViewController.HEIGHT / 2 - 100, 100, 100, 0, 360);
    }

}
