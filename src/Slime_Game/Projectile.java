package Slime_Game;

import java.awt.Color;

import Graphics.Ellipse;

/**
 * @author bean-b
 * @author Atibrewa
 * @author Mkleit
 * 
 * Creates projectiles that show the user when a tower is hitting an enemy
 */
public class Projectile {
    double x;
    double y;
    int timesToMove;
    int timesMoved;
    double xVeloc;
    double yVeloc;

    Ellipse sprite;

    /**
     * Creates a projectile and moves it from starting point (tower) to final point (enemy)
     * @param xStart starting x-position on canvas
     * @param yStart starting y-position on canvas
     * @param xFinal final x-position on canvas
     * @param yFinal final y-position on canvas
     */
    Projectile(double xStart, double yStart, double xFinal, double yFinal) {
        this.x = xStart;
        this.y = yStart;
        this.xVeloc = (xFinal - xStart) * 10;
        this.yVeloc = (yFinal - yStart) * 10; 
        
        this.sprite = new Ellipse(GameHandler.chordToPixel(x), GameHandler.chordToPixel(y), 7, 7);
        sprite.setFillColor(Color.BLACK);

        this.timesToMove = 5;
    }

    /**
     * deleted the projectile if it has already made it's designated number of moves, else continues to move it
     */
    public void update() {
        if(timesMoved >= timesToMove){
            GameHandler.kill(this);
        }
        else{
            sprite.moveBy(xVeloc, yVeloc);
            timesMoved ++;
        }
    }

    /**
     * returns ellipse of the projectile to add to canvas
     * @return
     */
    public Ellipse getSprite() {
        return sprite;
    }
}