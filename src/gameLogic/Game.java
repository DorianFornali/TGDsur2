package gameLogic;

import gameView.AssetManager;
import gameView.ViewController;
import gameView.panels.MainScreen;
import inputs.InputController;
import observerPattern.GameEvent;
import observerPattern.Observer;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

import javax.swing.*;

/**
 * This class is responsible for the game's logic.
 */
public class Game implements Runnable, Observer {
    private static Game gameInstance;
    private final ViewController viewController;
    private final InputController inputController;


    private Thread gameLoop;
    private int FPS_SET = 144, UPS_SET = 60;

    private Stage currentStage;

    public static boolean PAUSED = false, IN_GAME = false;

    // If the speed of the game has been upgraded (UPS limit higher)
    public static int CURRENT_SPEED_FACTOR = 1;


    public Game() {
        Game.gameInstance = this;
        viewController = new ViewController();
        inputController = new InputController(viewController);
        bindInputController();
        viewController.setCurrentPanel(new MainScreen(viewController));
        startGameLoop();
        AssetManager.getInstance().loadAssets();
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

    public void restartGameLoop(){
        gameLoop.interrupt();
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
        if(currentStage != null && !PAUSED)
            currentStage.update();

    }

    public InputController getInputController() {
        return inputController;
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

    public int getUPS(){
        return UPS_SET;
    }

    public void setUPS(int ups){
        UPS_SET = ups;
    }

    public void fastenTheGame() {
        if(!IN_GAME)
            return;
        switch(Game.CURRENT_SPEED_FACTOR){
            case 1:
                setUPS(getUPS()+60);
                Game.CURRENT_SPEED_FACTOR = 2;
                restartGameLoop();
                break;
            case 2:
                setUPS(getUPS()+60);
                Game.CURRENT_SPEED_FACTOR = 3;
                getInstance().restartGameLoop();
                break;
            default:
                setUPS(60);
                Game.CURRENT_SPEED_FACTOR = 1;
                getInstance().restartGameLoop();
                break;
        }
    }

    @Override
    public void receiveEventNotification(GameEvent event) {
        String typeEvent = event.getEventType();
        switch(typeEvent) {
            case "TEST_EVENT":
                System.out.println("Test event received by Game");
                break;
            case "FASTEN":
                fastenTheGame();
                break;
            default:
                break;
        }
    }
}
