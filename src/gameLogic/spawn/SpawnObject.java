package gameLogic.spawn;

import gameLogic.entity.EnemyType;

/**
 * This class represents a simple tuple containing three elements. Will correspond to each entry in the stage Json
 */
public class SpawnObject {
    // The type of the enemy, fast tank ...
    public EnemyType type;
    // The row in which the enemy shall spawn
    public int row;
    // Delay before next spawn
    public float delay;


    public SpawnObject(EnemyType enemyType, int row, float delay) {
        this.type = enemyType;
        this.row = row;
        this.delay = delay;
    }
}
