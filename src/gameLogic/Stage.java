package gameLogic;

import gameLogic.entity.Enemy;
import gameLogic.entity.EnemyFactory;
import gameLogic.entity.Entity;
import gameLogic.entity.Tower;
import gameView.ViewController;

import java.util.ArrayList;
import java.util.List;

public class Stage {
    /** The game board, contains the towers the player has placed */
    public Tower[][] gameBoard;

    /** The list of alive entities in the borad. Alive means they must be rendered in the View controller */
    public static List<Entity> entities;
    public EnemyFactory enemyFactory;

    /** The amount of rows and columns in the gameBoard */
    public static int nrows = 7, mcols = 10;

    public int playerHealth;

    public Stage(){
        this.gameBoard = new Tower[nrows][mcols];
        entities = new ArrayList<Entity>();
        this.enemyFactory = new EnemyFactory();
        this.playerHealth = 5;
        System.out.println("Initialized Stage");
        Enemy e = null;
        for(int i = 0; i < 5; i++) {
            e = enemyFactory.createWeakEnemy(i % 7);
            if(e != null)
                entities.add(e);
        }

    }

    public void update(){
        List<Enemy> enemiesToRemove = new ArrayList<Enemy>();
        // Update all entities
        for(Entity e : entities){
            if(e != null) {
                if(e instanceof Enemy) {
                    Enemy enemy = (Enemy) e;
                    if(((Enemy) e).toRemove()){
                        // We need to remove the enemy from the list of entities outside the loop
                        enemiesToRemove.add(enemy);
                    }
                }
                e.update();
            }
        }

        for(Enemy e : enemiesToRemove) {
            entities.remove(e);
        }

        // Update all towers
        for(int i = 0; i<nrows; i++){
            for(int j = 0; j<mcols; j++){
                if(gameBoard[i][j] != null){
                    gameBoard[i][j].update();
                }
            }
        }

    }

    public static void removeFromEntities(Entity e){
        entities.remove(e);
    }

}
