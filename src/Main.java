import gameLogic.Game;
import gameView.ViewController;
import inputs.InputController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
        });
    }
}