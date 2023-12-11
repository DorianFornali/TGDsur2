package gameLogic;

import gameView.ViewController;
import gameView.panels.TestPanel;
import inputs.InputController;

import javax.swing.*;

/**
 * This class is responsible for the game's logic.
 */
public class Game implements Runnable {
    private static Game gameInstance;
    private final ViewController viewController;
    private final InputController inputController;

    private Thread gameLoop;
    private final int FPS_SET = 144, UPS_SET = 144;

    public Game() {
        inputController = new InputController(this);
        viewController = new ViewController(this);
        bindInputController();
        viewController.setCurrentPanel(new TestPanel(viewController, true));

        startGameLoop();
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
    }

    public InputController getInputController() {
        return inputController;
    }
}
