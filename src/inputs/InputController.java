package inputs;

import gameView.ViewController;

import java.awt.event.MouseListener;

/**
 * This class is responsible for handling all the inputs from the user.
 */
public class InputController {
    private MouseController mouseController;
    private KeyboardController keyboardController;

    public InputController(ViewController vc) {
        this.mouseController = new MouseController();
        this.keyboardController = new KeyboardController(vc);
    }

    public MouseListener getMouseController() {
        return this.mouseController;
    }
    public KeyboardController getKeyboardController(){
        return this.keyboardController;
    }

}


