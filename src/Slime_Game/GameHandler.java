package Slime_Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import Slime_Game.Towers.Tower;
import Graphics.CanvasWindow;
import Graphics.FontStyle;
import Graphics.GraphicsObject;
import Graphics.GraphicsText;
import Graphics.Image;
import Graphics.Point;

/**
 * @author Bean Baumstark, GitHub: bean-b
 * @author Miriam Kleit, GitHub: Mkleit
 * @author Aahanaa Tibrewal, GitHub: Atibrewa
 *  
 * Main class, contains the arraylist for most of the game objects and methods to update them
 */
public class GameHandler {  
    public static final GameMap GAME_MAP = new GameMap("E3 S9 S7 E2 N4 E2 N5 N5 E9 E2 S7 W3 N4 W2 S7 W4 S7");
    public static final int X_GRID_SIZE = 20;
    public static final int Y_GRID_SIZE = 20;
    public static final int TILE_SIZE = 40;
    public static final int UI_WIDTH = 400;
    public static final int gridWidth = X_GRID_SIZE * TILE_SIZE;
    public static final int gridHeight = Y_GRID_SIZE * TILE_SIZE;
    public static final int DELAY = 200;
    public static final int NOT_UPDATED_DELAY = 1;
    public static final int TICK_DIV = 5;
    public static final double SELL_MULT = .75;

    private static Tile selectedTile;
    private static ArrayList<Tile> tileList;
    private static CanvasWindow canvas;
    @SuppressWarnings ("unused")
    private static UI userInterface;

    private static ArrayList<Enemy> enemyList;
    private static ArrayList<Tower> towerList;
    private static ArrayList<Projectile> projectileList;
    private static WaveHandler waveHandler;

    private static int dificulty;
    private static int money;
    private static int lives;
    private static int waveTick;
    private static int tick;
    private static boolean roundRunning;
    private static Wave curWave;
    private static int wave;

    public static void main(String[] args) {
        startGame();
        chooseDificulty();
    }
    /**
     * Creates a JFrame which allows the user to select a dificulty option, which influces enemy statics, starting money and money each round
     */
    private static void chooseDificulty() {
        JFrame jFrame = new JFrame();

        JComboBox<String> jComboBox = new JComboBox<>(new String[] {"Easy", "Normal", "Hard"});
        jComboBox.setBounds(80, 50, 140, 20);

        JButton jButton = new JButton("Done");
        jButton.setBounds(100, 100, 90, 20);

        jFrame.add(jButton);
        jFrame.add(jComboBox);
        
        jFrame.setLayout(null);
        jFrame.setSize(350, 250);
        jFrame.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(jComboBox.getItemAt(jComboBox.getSelectedIndex())){
                    case("Easy"):{
                        dificulty = 0;
                        money = 200;
                        break;
                    }
                    case("Normal"):{
                        dificulty = 1;
                        money = 175;
                        break;
                    }
                    case("Hard"):{
                        dificulty = 2;
                        money = 150;
                        break;
                    }
                }
                jFrame.setVisible(false);
                jFrame.dispose();
            }
        });
    }
    /**
     * initializes game variables and sets the canvas animate lambda and mouse down / up lambdas
     */
    private static void startGame() {
        canvas = new CanvasWindow("Slimes!", X_GRID_SIZE * TILE_SIZE + UI_WIDTH, Y_GRID_SIZE * TILE_SIZE);
        canvas.setBackground(Color.GRAY);

        tileList = new ArrayList<Tile>();
        enemyList = new ArrayList<Enemy>();
        towerList = new ArrayList<Tower>();
        projectileList = new ArrayList<Projectile>();
        waveHandler = new WaveHandler();

        dificulty = -1;

        roundRunning = false;
        tick = 0;
        waveTick = 0;
        wave = 0;

        money = 175;
        lives = 5;

        makeGrid();
        userInterface = new UI(X_GRID_SIZE*TILE_SIZE, UI_WIDTH, Y_GRID_SIZE * TILE_SIZE, canvas);
        setMap(GAME_MAP);

        canvas.animate((event) -> {
            updateCanvas();
            if(roundRunning && (tick % TICK_DIV == 0)) {
                waveTick++;
                if(waveTick >= curWave.getDelays()) {
                    waveTick = 0;
                    Enemy e = curWave.getNextEnemy();
                    if(e != null){
                        enemyList.add(e);
                    }
                }
                updateEnemies();
                updateTowers();
                updateCanvas();
                canvas.pause(DELAY / TICK_DIV);
                if(!enemiesAlive() && curWave.getDone() == true) {
                    resetEnemies();
                    wave++;
                    roundRunning = false;
                }
            }
            loseGame();
            winGame();
            canvas.pause(NOT_UPDATED_DELAY);
        });
        
        canvas.onMouseDown(event -> {
            for (Tile t : tileList) {
                if(t.getTileSelected()) {
                    t.deselect();
                }
                if(canvas.getElementAt(event.getPosition()).equals(t.getRectangle()) && !t.getIsPath()) { 
                    t.select(); 
                    selectedTile = t;
                }
            }
            for (Tower tower : towerList) {
                Image towerSprite = tower.getSprite();
                if(towerSprite.isInBounds(event.getPosition())) {
                    tower.getTile().select();
                    selectedTile = tower.getTile();
                }
            }
            UI.updateSlimeInfo(event.getPosition());
        }); 

    }
    /**
     * Creates the grid 
     */
    private static void makeGrid() {
        for(int x = 0; x < X_GRID_SIZE; x++) {
            for(int y = 0; y < Y_GRID_SIZE; y++) {
                Tile g = new Tile(TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE, x, y);
                tileList.add(g);
                canvas.add(g);
            }
        }
    }
    /**
     * Sets the active tile of grid based on the map.
     */
    private static void setMap(GameMap gameMap) {
        int x = 0;
        int y = 0;
        Character direction = 'E';
        String path = gameMap.getPath();
        getTileFromCord(x, y).setPath(true);
        for(Character c: path.toCharArray()) {
            if(Character.isDigit(c)){
                for(int i = 0; i < Character.getNumericValue(c); i++) {
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
                    getTileFromCord(x, y).setPath(true);
                }
            } else {
                direction = c;
            }
        }
    }
    /**
     * Loops through the enemylist and updates each enimies position acordingly. If the enemy is on the final tile, reduces lives by one and removes that enemy
     */
    private static void updateEnemies() {
        for(int i = 0; i < enemyList.size(); i++) {
            Enemy e = enemyList.get(i);
            if(e.getHp() < e.getMaxHP() && e.getRegen() >0 || (e.getRegen() < 0)) {
                e.changeHP(e.getRegen());
            }
            for(int ii=0; ii<e.getSpeed(); ii++) {
                boolean b = e.moveToNextSquare(GAME_MAP);
                if(b && e.getAlive()) {
                    if(e.getMaxHP() > 750){
                        lives -= 2;
                    }
                    else{
                        lives -= 1;
                    }
                    e.kill();
                }
            }
        }
    }

    private static void updateTowers() {
        for (Tower t : towerList) {
            t.attack();
        }
    }

    private static void updateCanvas() {
        updateProjectile();
        tick++;
        if(roundRunning) {
            for(Enemy e : enemyList) {
                if(e.getAlive())
                    canvas.add(e.getSprite());
            }
        }
        UI.updateText();
        canvas.draw();
    }

    private static void updateProjectile() {
        for(int i = 0; i < projectileList.size(); i++) {
            canvas.add(projectileList.get(i).getSprite());
            projectileList.get(i).update();
        }
    }

    private static void resetEnemies() {
        enemyList.clear();
    }
    
    public static void kill(Enemy e) {
        try {
            canvas.remove(e.getSprite());
        } catch (Exception exception) {

        }
        enemyList.remove(e);
    }

    public static void kill(Projectile projectile) {
        try {
            canvas.remove(projectile.getSprite());
        } catch (Exception e) {

        }
        projectileList.remove(projectile);
    }

    public static void projectileToFrom(Point orgin, Point destination, GraphicsObject sprite) {
        projectileList.add(new Projectile(orgin.getX(), orgin.getY(), destination.getX(), destination.getY()));
    }

    public static void sell() {
        Tower tower = returnTowerOnTile(selectedTile);
        if (tower != null) {
            changeMoney((int) (tower.returnCost() * SELL_MULT));
            towerList.remove(tower);
            canvas.remove(tower.getSprite());
        }
    }

    public static void changeMoney(int i) {
        money += i;
    }

    static Wave nextWave() {
        Wave wave = waveHandler.getNextWave();
        return wave;
    }

    private static boolean enemiesAlive() {
        for(Enemy e : enemyList) {
            if(e.getAlive())
                return true;
        }
        return false;
    }

    public static ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public static Tile getTileFromCord(int x, int y) { //cord = index       cord != pixel
        Tile tile = null;
        for(Tile t : tileList) {
            if(t.getXCord() == x && t.getYCord() == y) {
                tile = t;
            }
        }
        return tile;
    }

    public static Tile selectedTile() {
        return selectedTile;
    }

    static Tower returnTowerOnTile(Tile tile) {
        for(Tower t : towerList) {
            if(t.getTile() == tile) {
                return t;
            } 
        }
        return null;
    }

    public static int getMoney() {
        return money;
    }

    public static ArrayList<Tile> getTileList() {
        return tileList;
    }
    
    public static int getTick() {
        return tick;
    }

    public static int getLives(){
        return lives;
    }

    public static int getWave(){
        return wave;
    }
    
    public static double chordToPixel(double n){
        return n * GameHandler.X_GRID_SIZE * 2 + GameHandler.X_GRID_SIZE * .5;
    }

    public static boolean getRoundRunning() {
        return roundRunning;
    }
    public static void setCurWave(Wave w) {
        curWave = w;
    }

    public static void setRoundrunning(boolean b) {
        roundRunning = b;
    }

    public static Wave getCurWave() {
        return curWave;
    }

    public static void addTower(Tower tower) {
        towerList.add(tower);
    }

    private static void loseGame() {
        if (lives <= 0) {
            GraphicsText lostText = new GraphicsText("YOU LOST :)", gridWidth/4, gridHeight/2);
            lostText.setFont("Comic Sans MS", FontStyle.BOLD, gridHeight/10);
            canvas.add(lostText);  
        } 
    }

    private static void winGame() {
        if (lives > 0 && wave >= WaveHandler.getWaveListSize()) {
            GraphicsText winText = new GraphicsText("YOU WON!", gridWidth/4, gridHeight/2);
            winText.setFont("Comic Sans MS", FontStyle.BOLD, gridHeight/10);
            canvas.add(winText);
        }
    }

    public static int getDificulty() {
        return dificulty;
    }
}
