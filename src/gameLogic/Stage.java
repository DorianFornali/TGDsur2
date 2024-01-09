package gameLogic;

import gameLogic.entity.Entity;
import gameLogic.entity.Tower;

import java.util.ArrayList;
import java.util.List;

public class Stage {
    /** The game board, contains the towers the player has placed */
    public Tower[][] gameBoard;

    /** The list of alive entities in the borad. Alive means they must be rendered in the View controller */
    public List<Entity> entities;

    /** The amount of rows and columns in the gameBoard */
    public static final int nrows = 5, mcols = 6;

    public Stage(){
        this.gameBoard = new Tower[nrows][mcols];
        this.entities = new ArrayList<Entity>();
        System.out.println("Initialized Stage");
    }


}
