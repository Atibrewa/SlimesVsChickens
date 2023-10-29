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
 * Creates a Blue Slime tower and handles it's attack method
 * Blue attacks the furthest enemy in its range!
 */
public class BlueSlime implements Tower {
    private int attackDamage;
    private int attackRadius;
    private int cost;
    private Tile tileLocation;
    private Image sprite;
    private int chargeTime;
    public static final String STATS = "Blue: dmg " + 5 +" 2 dmg over time" + ";\r\n Attack Time " + 3 + "; radius " + 2 +"; cost " + 100;

    /** Creates a Blue sLime on the tile given as argument with the following stats:
     * attack damage: 5
     * attack radius: 2
     * cost: 100
     * Shoots first furthest enemy within the range
     * @param tile location where the tower is created
     */
    public BlueSlime(Tile tile) {
        cost = 100; 
        attackDamage = 5;
        attackRadius = 2;
        tileLocation = tile;
        sprite = new Image("Blue.png");
    }

    /**
     * attacks the furthest enemy in its range and reduces its Helth and adds damage over time if it doesnt already have regen or damage over time
     */
    @Override
    public void attack() {
        Enemy enemy = enemyInRadius();
        if(enemy != null){
            if(chargeTime < 3){
                chargeTime++;
            }
            else{
                chargeTime = 0;
                GameHandler.projectileToFrom(new Point(tileLocation.getXCord(), tileLocation.getYCord()), enemy.getPoint(), sprite);
                enemy.changeHP(-attackDamage);
                if(enemy.getRegen() == 0){
                    enemy.changeRegen(-2);
                }
            }
        }
    }

    /** 
     * checks if any enemy is on a tile w/in the radius and returns the furthest one
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
