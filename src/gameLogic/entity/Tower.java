package gameLogic.entity;

import gameLogic.Game;
import gameLogic.Stage;
import gameView.panels.GameScreen;


public class Tower extends Entity{

    private Game game = Game.getInstance();
    private int damage;
    private boolean canShoot;
    private boolean canBlock;
    private TowerType towerType;
    public static final int ATTACK_TOWER_PRICE = 100;
    public static final int MULTI_TOWER_PRICE = 325;
    public static final int GLOBAL_TOWER_PRICE = 500;
    public static final int DEFENSIVE_TOWER_PRICE = 50;
    public static final int MONEY_TOWER_PRICE = 50;


    private boolean isAlive;


    /** Updates the tower, will call a function corresponding of the tower's type 
     * We choose to do it that way instead of one class = one tower 
     * */
    public void update(){
        super.update();

        if(getHealth() <= 0){
            // Tower dies
            System.out.println("Tower died");
            setisAlive(false);
        }

        else if(getHealth() <= getMaxHealth()/2){
            // Tower is hurt
            // TODO! Change spritesheet for a more "damaged" one
        }

        switch(this.towerType){
            case ATTACK:
                updateAttackTower();
                break;
            case MULTI:
                updateMultiTower();
                break;
            case GLOBAL:
                updateGlobalTower();
                break;
            case DEFENSIVE:
                updateDefensiveTower();
                break;
            case MONEY:
                updateMoneyTower();
                break;
        }
    }

    /** The update function for the money tower */
    private void updateMoneyTower() {
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            System.out.println("Money tower generated money");
            // The tower can "shoot", in this case it simply generates money
            game.getCurrentStage().setPlayerMoney(game.getCurrentStage().getPlayerMoney() + 15);
            //TODO! Do some visual effect to show the player that the tower generated money -> the GameScreen must do it
            //TODO! send message to GameScreen
            previousFiring = System.nanoTime();
        }
    }

    private void updateDefensiveTower() {
        //Do nothing, just protect the player
    }

    private void updateGlobalTower() {
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            // The tower can "shoot", in this case it simply attacks every enemy on the map
            for(Enemy e : game.getCurrentStage().enemiesAlive){
                e.setHealth(e.getHealth() - getDamage());
            }
            previousFiring = System.nanoTime();
        }
    }

    private void updateMultiTower() {
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            shootProjectile(0);
            int row = Stage.getRowFromY((int)getY());
            switch (row){
                case (0):
                    System.out.println("tout en haut");
                    shootProjectile(0);
                    shootProjectile(1);
                    break;
                case (6):
                    System.out.println("tout en bas");
                    shootProjectile(0);
                    shootProjectile(-1);
                    break;
                default:
                    shootProjectile(0);
                    shootProjectile(1);
                    shootProjectile(-1);
                    break;
            }
            previousFiring = System.nanoTime();
        }
    }

    private void updateAttackTower() {
        if(System.nanoTime() - previousFiring >= firingRate/Game.CURRENT_SPEED_FACTOR){
            shootProjectile(0);
            previousFiring = System.nanoTime();
        }
    }

    public void setCanShoot(boolean canShoot){
        this.canShoot = canShoot;
    }

    public void setCanBlock(boolean canBlock){
        this.canBlock = canBlock;
    }
    
    public void setTowerType(TowerType towerType){
        this.towerType = towerType;
    }

    public void setisAlive(boolean isAlive) {
    	this.isAlive = isAlive;
    }

    public boolean isAlive() {
    	return this.isAlive;
    }


    public void setHitbox(){
        int cellWidth = GameScreen.calculateCellWidth();
        int cellHeight = GameScreen.calculateCellHeight();

        int hitboxWidth = (int) (cellWidth*0.8), hitboxHeight = cellHeight/2;
        super.setHitbox(5000, 5000, hitboxWidth, hitboxHeight);
    }

    /** Creates a projectile at the tower location */
    private void shootProjectile(int rowOffSet){
        int YOffSet = rowOffSet * GameScreen.calculateCellHeight();
        Projectile p = new Projectile();
        p.setX(getX() + (float) getHitbox().getWidth() /2);
        p.setY((float) (getY() + getHitbox().getHeight()/2) + YOffSet);
        p.setHitbox(-500, -500, GameScreen.calculateCellWidth()/4, GameScreen.calculateCellHeight()/4);
        p.setSpeed(getSpeed());
        p.setDamage(getDamage());
        game.getCurrentStage().projectilesAlive.add(p);
    }

}
