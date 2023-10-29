package Slime_Game.Towers;

import java.util.List;
import Slime_Game.Enemy;
import Slime_Game.GameHandler;
import Slime_Game.Tile;
import Graphics.Image;
import Graphics.Point;

/**
 * @author Atibrewa
 * @author bean-b
 * @author Mkleit
 * 
 * Creates a Yellow Slime tower and handles it's attack method
 * Yellow has bigger range and higher damage rate than Green
 * Also costs more
 */
public class YellowSlime implements Tower {
    private int attackDamage;
    private int attackRadius;
    private int cost;
    private Tile tileLocation;
    private Image sprite;
    public static final String STATS = "Yellow: dmg " + 6 + "; Attack Time " + .5 + "; \r\n radius " + + 3 +"; cost " + 125;

    /** Creates a Yellow sLime on the tile given as argument with the following stats:
     * attack damage: 6
     * attack radius: 3
     * cost: 125
     * Shoots first enemy w/in range
     * @param tile location where the tower is created
     */
    public YellowSlime(Tile tile){
        sprite = new Image("Yellow.png");
        cost = 125; 
        attackDamage = 6;
        attackRadius = 3;
        tileLocation = tile;
    }

    /** 
     * checks if any enemy is on a tile w/in the radius and returns the first one that is 
     */
    private Enemy enemyInRadius(){
        int x = tileLocation.getXCord();
        int y = tileLocation.getYCord();
        int futherestEnemy = -1;
        List<Enemy> enemyList = GameHandler.getEnemyList();
        Enemy toReturn = null;
        for(Enemy e : enemyList){
            double enemyX = e.getPoint().getX();
            double enemyY = e.getPoint().getY();
            if(enemyX + attackRadius >= x && enemyX - attackRadius <= x){
                if(enemyY + attackRadius >= y && enemyY - attackRadius <= y){
                    if(e.getTimesMoved() > futherestEnemy){
                        futherestEnemy = e.getTimesMoved();
                        toReturn = e;
                    }
                }
            }
        }
        return toReturn;
    }

    /** 
     * Attacks the first enemy w/in range and reduces its health 
     */
    @Override
    public void attack() {
        Enemy enemy = enemyInRadius();
        if(enemy != null){
            GameHandler.projectileToFrom(new Point(tileLocation.getXCord(), tileLocation.getYCord()), enemy.getPoint(), sprite);
            GameHandler.projectileToFrom(new Point(tileLocation.getXCord() - .2, tileLocation.getYCord() -.2), enemy.getPoint(), sprite);
            enemy.changeHP(-attackDamage);
            enemy.changeHP(-attackDamage);
        }
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public Tile getTile(){
        return tileLocation;
    }
    
    @Override
    public boolean canBuy() {
        if(GameHandler.getMoney() >= cost){
            return true;
        }
        return false;
    }
    
    @Override
    public int returnCost(){
        return cost;
    }

    @Override
    public void setImageCentered(){
        sprite.setCenter(GameHandler.selectedTile().getPixelCenter());
        sprite.setScale(.45);
    }

}
