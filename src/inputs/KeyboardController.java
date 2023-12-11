package inputs;
import gameView.panels.TestPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements KeyListener{
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {

            case KeyEvent.VK_ESCAPE:
                System.out.println("Closing process ...");
                System.exit(0);
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
