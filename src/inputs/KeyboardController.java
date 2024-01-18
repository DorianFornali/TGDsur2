package inputs;

import gameLogic.Game;
import gameView.ViewController;
import observerPattern.GameEvent;
import observerPattern.Observable;
import observerPattern.Observer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyboardController implements Observable, KeyListener {
    private List<Observer> observers = new ArrayList<>();

    public KeyboardController(ViewController vc) {
        observers.add(vc);
        observers.add(Game.getInstance());
        System.out.println("Creation d'un Keyboard COntroller");
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(GameEvent event) {
        for (Observer observer : observers) {
            observer.receiveEventNotification(event);
        }
    }

    public void generateEvent(String eventType, Object eventData) {
        GameEvent event = new GameEvent(eventType, eventData);
        notifyObservers(event);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.out.println("Sending pause event");
                generateEvent("PAUSE", null);
                break;
            case KeyEvent.VK_T:
                System.out.println("Sending fasten event");
                generateEvent("FASTEN", null);
                break;
            case KeyEvent.VK_NUMPAD0:
                System.out.println("Pressed 0, sending test event");
                generateEvent("TEST_EVENT", null);
            default:
                System.out.println("Key pressed");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
