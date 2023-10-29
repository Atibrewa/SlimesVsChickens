package Slime_Game;

import Graphics.Image;

/**
 * @author bean-b
 * @author MKleit
 * @author Atibrewa
 * 
 * contains methods to return the required enemy with its specific stats
 */
public class EnemyIndex {
    /**
     * Returns a white chicken with the following stats:
     * Health Power: 70 + dificulty * 5
     * Speed: 1
     * Money: 5
     * @return Enemy
     */
    public static Enemy getWhiteChicken(){
        return new Enemy(70 + GameHandler.getDificulty() * 5, 1, getWhiteChickenSprite(), 5);
    }

    /**
     * Returns a brown chicken with the following stats:
     * Health Power: 140 + dificulty * 10
     * Speed: 1
     * Money: 7
     * @return Enemy
     */
    public static Enemy getBrownChicken(){
        return new Enemy(140 + GameHandler.getDificulty() * 10, 1, getBrownChickenSprite(), 7);
    }

    /**
     * Returns a black chicken with the following stats:
     * Health Power: 150 + dificulty * 10
     * Speed: 1
     * Money: 15
     * Armor: 5 + dificulty 
     * @return Enemy
     */
    public static Enemy getBlackChicken(){
        return new Enemy(150 + GameHandler.getDificulty() * 10, 1, getBlackChickenSprite(), 15, 5 + GameHandler.getDificulty(), 0);
    }

    /**
     * Returns a blue chicken with the following stats:
     * Health Power: 120 + dificulty * 5
     * Speed: 1
     * Money: 10 
     * Regen: 2 + dificulty
     * @return Enemy
     */
    public static Enemy getBlueChicken(){
        return new Enemy(120 + GameHandler.getDificulty() * 5, 1, getBlueChickenSprite(), 12, 0, 2 + GameHandler.getDificulty());
    }

    /**
     * Returns a yellow chicken with the following stats:
     * Health Power: 110 + dificulty * 20
     * Speed: 2
     * Money: 12
     * @return Enemy
     */
    public static Enemy getYellowChicken(){
        return new Enemy(110 + GameHandler.getDificulty() * 20, 2, getYellowChickenSprite(), 12);
    }

    /**
     * Returns a MEGA chicken with the following stats:
     * Health Power: 900 + 100 * dificulty
     * Speed: 1
     * Money: 40
     * @return Enemy
     */
    public static Enemy getMegaChicken(){
        return new Enemy(900 + 100 * GameHandler.getDificulty(), 1, getBigChickenSprite(), 40);
    }

    /**
     * returns whiteChicken image
     */
    public static Image getWhiteChickenSprite(){
        Image toReturn = new Image("WhiteChicken.png");
        toReturn.setScale(.3);
        return toReturn;
    }

    /**
     * returns blackeChicken image
     */
    public static Image getBlackChickenSprite(){
        Image toReturn = new Image("BlackChicken.png");
        toReturn.setScale(.3);
        return toReturn;
    }

    /**
     * returns brownChicken image
     */
    public static Image getBrownChickenSprite(){
        Image toReturn = new Image("BrownChicken.png");
        toReturn.setScale(.3);
        return toReturn;
    }

    /**
     * returns blueChicken image
     */
    public static Image getBlueChickenSprite(){
        Image toReturn = new Image("BlueChicken.png");
        toReturn.setScale(.3);
        return toReturn;
    }

    /**
     * returns BIGChicken image scaled to be larger than others
     */
    public static Image getBigChickenSprite() {
        Image toReturn = new Image("WhiteChicken.png");
        toReturn.setScale(.4);
        return toReturn;
    }

    /**
     * returns yellowChicken image
     */
    public static Image getYellowChickenSprite() {
        Image toReturn = new Image("YellowChicken.png");
        toReturn.setScale(.3);
        return toReturn;
    }
}
