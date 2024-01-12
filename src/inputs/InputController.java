package inputs;

import gameLogic.Game;
import gameView.ViewController;
import observerPattern.GameEvent;
import observerPattern.Observable;
import observerPattern.Observer;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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


