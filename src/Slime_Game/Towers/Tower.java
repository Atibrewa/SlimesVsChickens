package Slime_Game.Towers;

import Slime_Game.Tile;
import Graphics.Image;

/**
 * @author Atibrewa
 * @author bean-b
 * @author Mkleit
 * 
 * Basic interface for the towers, allows the towers to attack, be bought, etc.
 */
public interface Tower {
    /** Attacks the enemies, reducing their hp. Implementation differs depending on the tower */
    abstract void attack();

    /** Checks cost of tower and money left, returns whether money is greater than cost or not */
    abstract boolean canBuy();

    /** Gets the tile tower is on */
    abstract Tile getTile();

    /**returns cost of the tower as integer for easy transactions*/    
    abstract int returnCost();

    /** returns the tower's image */
    abstract Image getSprite();

    /** sets the tower's location to center of tile */
    abstract void setImageCentered();

}
