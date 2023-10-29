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
 * Creates a basic tower (Green Slime) and handles it's attack method
 */
public class GreenSlime implements Tower{
    private int attackDamage;
    private int attackRadius;
    private int cost;
    private Tile tileLocation;
    private Image sprite;
    private int chargeTime;
    public static final String STATS = "Green: dmg " + 19 + "; Attack Time " + 2 + "; \r\n radius " + 2 +"; cost " + 50;

    /** Creates a Green sLime on the tile given as argument with the following stats:
     * attack damage: 19
     * attack radius: 2
     * cost: 50
     * Shoots first enemy w/in range
     * @param tile location where the tower is created
     */
    public GreenSlime(Tile tile){
        cost = 50; 
        attackDamage = 19;
        attackRadius = 2;
        tileLocation = tile;
        sprite = new Image("Green.png");
    }
    
    /** 
     * Attacks the first enemy w/in range and reduces its health 
     */
    @Override
    public void attack() {
        Enemy enemy = enemyInRadius();
        if(enemy != null){
            if(chargeTime < 1){
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
     * checks if any enemy is on a tile w/in the radius and returns the first one
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

