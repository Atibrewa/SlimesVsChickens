package Slime_Game;

import java.util.ArrayList;

import Graphics.Image;
import Graphics.Point;

/**
 * @author bean-b
 * @author Mkleit
 * @author Atibrewa
 * 
 * Creates an enemy and keeps it's stats- Health Power, Damage caused to lves etc. 
 * and moves it along the path
 */
public class Enemy {
    private double hp;
    private double maxHp;
    private double speed;
    private int money;
    private boolean alive;
    private Image sprite;
    private int regen;
    private int armor;

    private Point point; //point is in index, not pixel coordinates
    private int timesMoved;
    private Character direction;

    /**
     * Creates an enemy with given statistics
     * @param regen regeneration rate
     * @param hp Health Power of Enemy
     * @param speed how many tiles it moves across at once
     * @param sprite Image
     * @param money The money we get for defeating it
     * @param armor resiests a flat amount of damage per attack 
     */
    Enemy(int hp, double speed, Image sprite, int money, int armor, int regen){
        this(hp, speed, sprite, money);
        this.armor = armor;
        this.regen = regen;
    }
    
    Enemy(double hp, double speed, Image sprite, int money){
        this.hp = hp;
        this.speed = speed;
        this.sprite = sprite;
        this.money = money;
        this.regen = 0;
        this.armor = 0;

        this.timesMoved = 0;
        this.direction = 'E';
        this.alive = true;
        this.maxHp = hp;
        setPoint(new Point(0, 0));
    }
    
    /**
     * Moves the enemy to the next Tile/Square and returns true when it reaches the end of the path
     * @param   gameMap
     * @return  true when it reaches end of path
     */
    public boolean moveToNextSquare(GameMap gameMap){
        int p = 0;
        int x = 0;
        int y = 0;
        String path = gameMap.getPath();
        boolean cIsDigit = false;
        for(Character c : path.toCharArray()){
            cIsDigit = Character.isDigit(c);
            if(cIsDigit){
                for(int i = 0; i < Character.getNumericValue(c); i++){
                    switch(direction){
                        case('E'):{
                            x++;
                            break;
                        }
                        case('S'):{
                            y++;
                            break;
                        }
                        case('W'):{
                            x--;
                            break;
                        }
                        case('N'):{
                            y--;
                            break;
                        }
                    }
                    p++;
                    if(p - 1 == timesMoved)
                        break;
                }
            if(p - 1 == timesMoved)
                break;
        }
        else
            direction = c;
        }
        timesMoved = p;
        setPoint(new Point(x, y));
        return p == gameMap.getLength();
    }

    /**
     * Kills off the enemy!
     */
    public void kill() {
        if(hp <= 0)
            GameHandler.changeMoney(money);
        GameHandler.kill(this);
        alive = false;
    }

    /**
     * Reduces Health of enemy by given integer value
     * @param n attackDamage of the tower that shot this enemy (How much damage is dont to the enemy)
     */
    public void changeHP(double n){
        if(n<0 && armor > 0){
            n += armor;
            if(n > -.5){
                n=-.5;
            }
        }
        hp += n;
        if(hp <= 0){
            kill();
        }
    }

    /**
     * Places the enemy on the required tile by converting the index position to the pixel position
     * @param point Point contains the index location (cord) of the enemy
     */
    public void setPoint(Point point) {
        this.point = point; 
        sprite.setCenter(new Point(GameHandler.chordToPixel(point.getX()),
        GameHandler.chordToPixel(point.getY())));
    } 

    public double getSpeed() {
        return speed;
    }

    public Image getSprite() {
        return sprite;
    }

    public Point getPoint() {
        return point;
    }

    /**
     * checks indexes and returns the tile that the enemy is currently on
     * @return Tile enemy is on
     */
    public Tile getTile(){
        ArrayList<Tile> tileList = GameHandler.getTileList();
        for(Tile t: tileList){
            Point tIndexPoint = new Point(t.getXCord(),t.getYCord());
            if(tIndexPoint.getX() == getPoint().getX() && tIndexPoint.getY() == getPoint().getY()){
                return t;
            }
        }
        return null;
    }

    public int getMoney() {
        return money;
    }

    /**
     * returns true if the enemy is still alive
     * @return
     */
    public boolean getAlive(){
        return alive;
    }

    public double getHp() {
        return hp;
    }

    /**
     * returns the number of times the enemy has moved along the path
     * @return
     */
    public int getTimesMoved() {
        return timesMoved;
    }

    /**
     * returns a new enemy of the same type and stats as itself
     * @return Enemy
     */
    public Enemy returnSelf(){
        return new Enemy(hp, speed, sprite, money);
    }

    /**
     * Returns the enemy's position and health remaining in string form
     */
    @Override
    public String toString(){
        return "X: " + point.getX() + " Y: " + point.getY() + " HP: " + hp;
    }

    public int getRegen(){
        return regen;
    }

    public double getMaxHP() {
        return maxHp;
    }

    /**
     * changes regeneration by adding n to the current rate
     * @param n int number to increase regen by
     */
    public void changeRegen(int n){
        regen += n;
    }
   
}
