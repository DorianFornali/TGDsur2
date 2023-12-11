package inputs;

import gameLogic.Game;
import gameView.ViewController;

import java.awt.event.MouseListener;

public class InputController {
    private Game game;
    private MouseController mouseController;
    private KeyboardController keyboardController;

    public InputController(Game game) {
        this.game = game;

        this.mouseController = new MouseController();
        this.keyboardController = new KeyboardController();

    }


    public MouseListener getMouseController() {
        return this.mouseController;
    }

    public KeyboardController getKeyboardController() {
        return this.keyboardController;
    }
}
