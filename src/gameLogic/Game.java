package gameLogic;

import gameView.ViewController;
import gameView.panels.MainScreen;
import inputs.InputController;
import observerPattern.GameEvent;
import observerPattern.Observable;
import observerPattern.Observer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the game's logic.
 */
public class Game implements Runnable, Observable {
    private static Game gameInstance;
    private final ViewController viewController;
    private final InputController inputController;

    private List<Observer> observers = new ArrayList<>();

    private Thread gameLoop;
    private final int FPS_SET = 144, UPS_SET = 60;

    private Stage currentStage;

    public Game() {
        inputController = new InputController();
        viewController = new ViewController();
        bindInputController();
        viewController.setCurrentPanel(new MainScreen(viewController));
        addObserver(viewController);
        startGameLoop();
        Game.gameInstance = this;
    }

    public static Game getInstance() {
        if(gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    private void bindInputController() {
        viewController.setInputController(inputController);
    }

    private void startGameLoop() {
        // GameLoop's initialization
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    @Override
    public void run() {
        // Game loop executed on a different thread

        int frames = 0; long lastCheck = 0;
        double timePerFrame = 1000000000.0 / FPS_SET; // Time per frame (nano seconds)
        double timePerUpdate = 1000000000.0 / UPS_SET; // Delay between updates (nano seconds)


        long previousTime = System.nanoTime();
        int updates = 0;
        double deltaU = 0;
        double deltaF = 0;

        while(!Thread.interrupted()) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1) {
                this.updateGameLogic();

                updates++;
                deltaU--;
            }

            if(deltaF >= 1) {
                SwingUtilities.invokeLater(viewController::renderGraphics);

                deltaF--;
                frames++;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS : " + frames + " UPS : " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void updateGameLogic() {
        // Game logic update
        //generateEvent("TEST_EVENT", null);
        if(currentStage != null)
            currentStage.update();


    }

    public InputController getInputController() {
        return inputController;
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
        for(Observer observer : observers) {
            observer.receiveEventNotification(event);
        }
    }

    /**
     * Generates an event and notifies the observers.
     * @param eventType The name of the event.
     * @param eventData The data associated with the event.
     */
    public void generateEvent(String eventType, Object eventData) {
        GameEvent event = new GameEvent(eventType, eventData);
        notifyObservers(event);
    }

    public Stage getCurrentStage(){
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public ViewController getViewController() {
        return viewController;
    }
}
