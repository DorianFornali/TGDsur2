package inputs;

import gameLogic.Game;
import gameView.ViewController;

import java.awt.event.MouseListener;

/**
 * This class is responsible for handling all the inputs from the user.
 */
public class InputController {
    private MouseController mouseController;

    public InputController(Game game) {
        this.mouseController = new MouseController();
    }

    public MouseListener getMouseController() {
        return this.mouseController;
    }
}
