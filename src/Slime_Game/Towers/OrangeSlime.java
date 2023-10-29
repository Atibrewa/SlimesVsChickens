package Slime_Game.Towers;

import java.util.ArrayList;
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
 * Creates an Orange Slime tower and handles it's attack method
 * Orange has a very small range but shoots multiple enemies at once by shooting in all directions
 * The attack damage increases with more enemies upto a maximum of 12 damage at once per enemy
 */
public class OrangeSlime implements Tower {
    private int attackDamage;
    private int attackRadius;
    private int cost;
    private Tile tileLocation;
    private Image sprite;
    ArrayList<Enemy> enemiesInRange = new ArrayList<>();
    public static final String STATS = "Orange: dmg " + "5-12, more aoe damage \r\n the more enemies nearby; \r\n Attack Time " + 1 + ";\r\n radius " + 1 +"; cost " + 75;

    /** Creates an Orange sLime on the tile given as argument with the following stats:
     * attack damage: 5
     * attack radius: 1
     * cost: 75
     * Shoots enemies in all directions withing the range
     * @param tile location where the tower is created
     */
    public OrangeSlime(Tile tile){
        cost = 75; 
        attackDamage = 5;
        attackRadius = 1;
        tileLocation = tile;
        sprite = new Image("Orange.png");
    }

    /** 
     * checks if enemy is on a tile w/in the radius, adds it to a list of enemies
     * Note: xCord and yCord refer to the coordinates/indexes in our tile system, not the pixel position
     */
    private void enemyInRadius(){
        enemiesInRange.clear();
        int x = tileLocation.getXCord();
        int y = tileLocation.getYCord();

        for(Enemy enemy: GameHandler.getEnemyList()) {
            Tile t = enemy.getTile();
            if (t != null) {
                if (t.getXCord() <= attackRadius + x && t.getXCord() >= x-attackRadius){
                    if (t.getYCord() <= attackRadius + y && t.getYCord() >= y-attackRadius){
                        enemiesInRange.add(enemy);
                    }
                }
            }
        }
    }
    
    /**
     * attacks all enemies in radius by shooting in all directions
     * the amount of damage depends on the number of enemies in range
     * does more damage, if there are more enemies, upto 12 damage at a time
     */
    @Override
    public void attack() {
        enemyInRadius();
        if(enemiesInRange.size() > 0){
            int xPos = tileLocation.getXCord();
            int yPos = tileLocation.getYCord();
            for(int x = -1; x < 2; x++){
                for(int y = -1; y < 2; y++){
                    if(x != 0 || y != 0){
                        GameHandler.projectileToFrom(new Point(xPos, yPos), new Point(xPos + x, yPos + y), sprite);
                    }
                }
            }
        }
        for (Enemy enemy : enemiesInRange){
            int thisAttackDamage = attackDamage + enemiesInRange.size() * 2;
            if(thisAttackDamage > 12){
                thisAttackDamage = 12;
            }
            enemy.changeHP(-thisAttackDamage);
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
