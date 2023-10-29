package Slime_Game;

import java.util.List;

/**
 * @author Atibrewa
 * @author bean-b
 * @author Mkleit
 * 
 * Contains a ist of enemies and feeds them into gamehandler to put onto the canvas
 */
public class Wave {
    private int enemyIndex;
    private List<Enemy> enemies;
    private int delay;

    /**
     * Takes a list of enemies and feeds them into gamehandler one by one, taking into account the delay time
     * @param enemies   list of enemies in the wave
     * @param delay     the delay time between enemies in the wave
     */
    Wave (List<Enemy> enemies, int delay) {
        this.enemies = enemies;
        this.delay = delay;
        enemyIndex = 0;
    }

    /**
     * reurns the delay time for the wave
     * @return
     */
    public int getDelays() {
        return delay;
    }

    /**
     * returns true if the wave is complete, and there are no enemies left
     * @return
     */
    public boolean getDone() {
        return enemyIndex >= enemies.size(); 
    }

    /**
     * returns the enemy that's next in the wave, if one exists
     * @return 
     */
    public Enemy getNextEnemy() {
        if (enemyIndex < enemies.size()) {
            enemyIndex ++;
            return enemies.get(enemyIndex - 1);
        } else {
            return null;
        }
    }
}
