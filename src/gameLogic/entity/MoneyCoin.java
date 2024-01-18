package gameLogic.entity;

import gameLogic.Game;
import gameView.AssetManager;
import gameView.ViewController;
import gameView.panels.GameScreen;

/**
 * The same way the global tower has its globalwave, the money tower has its MoneyGenerationIcon
 * that will be displayed when generating money
 */
public class MoneyCoin extends Entity {

    public boolean dead;

    public MoneyCoin() {
        // All these objects will be the same
        this.setSpriteSheet(AssetManager.getInstance().getSprite("moneyGenerationIcon"));
        this.setNSPRITES(11);
        dead = false;
    }

    @Override
    public void update() {
        if (dead) return;
        // We do not need any hitbox on that object so we skip the updateHitbox() part
        super.updateSprite();

        // We will update the coordinates of this object according to a math function to give it the
        // "movement" of a curb
        float deltaX = (GameScreen.moneyLocationX - getX());
        float deltaY = (GameScreen.moneyLocationY - getY());

        if (Math.abs(deltaX) < ViewController.WIDTH / 50 && Math.abs(deltaY) < ViewController.HEIGHT / 50) {
            // The coin reached the destination, generates money and stop updating/drawing coin
            Game.getInstance().getCurrentStage().setPlayerMoney(Game.getInstance().getCurrentStage().getPlayerMoney() + getDamage());
            dead = true;
        }

        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        float speedX = getSpeed() / distance * deltaX;
        float speedY = getSpeed() / distance * deltaY;

        setX(getX() + speedX);
        setY(getY() + speedY);
        setSpeed((float) (getSpeed() + 0.1));
    }

}
