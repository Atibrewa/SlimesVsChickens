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
 * Creates a Red Slime tower and handles it's attack method
 * Red does much higher damage but takes time to recharge
 */
public class RedSlime implements Tower{
    private int attackDamage;
    private int attackRadius;
    private int cost;
    private Tile tileLocation;
    private Image sprite;
    private int chargeTime;
    public static final String STATS = "Red: dmg " + 110 + "; Attack Time " + 5 + "; \r\n radius " + 1 +"; cost " + 75;
    
    /** Creates a Red SLime tower on the tile given as argument with the following stats:
     * attack damage: 110
     * attack radius: 1
     * cost: 75
     * Shoots first enemy w/in range, but takes time to recharge
     * @param tile location where the tower is created
     */
    public RedSlime(Tile tile){
        cost = 75; 
        attackDamage = 110;
        attackRadius = 1;
        tileLocation = tile;
        chargeTime = 0;
        sprite = new Image("Red.png");
    }
    
    /** 
     * Attacks the first enemy w/in range and reduces its health 
     */
    @Override
    public void attack() {
        Enemy enemy = enemyInRadius();
        if(enemy != null){
            if(chargeTime < 5){
                chargeTime++;
            }
            else{
                chargeTime = 0;
                GameHandler.projectileToFrom(new Point(tileLocation.getXCord(), tileLocation.getYCord()), enemy.getPoint(), sprite);
                enemy.changeHP(-attackDamage);
            }
        }
    }

    /** 
     * checks if enemy is on a tile w/in the radius 
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


