package gameLogic;

import gameLogic.entity.EnemyFactory;
import gameLogic.entity.Entity;
import gameLogic.entity.Tower;

import java.util.ArrayList;
import java.util.List;

public class Stage {
    /** The game board, contains the towers the player has placed */
    public Tower[][] gameBoard;

    /** The list of alive entities in the borad. Alive means they must be rendered in the View controller */
    public List<Entity> entities;
    public EnemyFactory enemyFactory;

    /** The amount of rows and columns in the gameBoard */
    public final int nrows = 7, mcols = 10;

    private int playerHealth;

    public Stage(){
        this.gameBoard = new Tower[nrows][mcols];
        this.entities = new ArrayList<Entity>();
        this.enemyFactory = new EnemyFactory();
        this.playerHealth = 5;
        System.out.println("Initialized Stage");
    }

    public void update(){
        // Update all entities
        for(Entity e : entities){
            if(e != null)
                e.update();
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


}
