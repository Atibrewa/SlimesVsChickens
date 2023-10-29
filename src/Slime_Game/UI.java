package Slime_Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Slime_Game.Towers.BlueSlime;
import Slime_Game.Towers.GreenSlime;
import Slime_Game.Towers.OrangeSlime;
import Slime_Game.Towers.RedSlime;
import Slime_Game.Towers.Tower;
import Slime_Game.Towers.YellowSlime;
import Graphics.ui.Button;
import Graphics.*;

/** 
 * @author Atibrewa
 * @author bean-b
 * @author Mkleit
 * 
 * Creates the user interface and all of its interactive parts 
 */
public class UI {
    public static final int TILE_SIZE = 40;
    public static Color FILL_COLOR = new Color(12, 139, 153);
    private List<String> colorList = List.of("Green", "Orange", "Red", "Blue", "Yellow");
    private List<String> enemyStringList = List.of("WhiteChicken", "BrownChicken", "BlueChicken", 
        "YellowChicken", "BlackChicken");
    private static ArrayList<Image> slimeImageList;
    private ArrayList<Tower> towerList;
    private ArrayList<Button> slimeButtonList;
    private static CanvasWindow canvas;

    private int UIwidth;
    private static GraphicsText moneyText;
    private static GraphicsText livesText;
    private static GraphicsText roundText;
    private static GraphicsText enemyInfoTitle;
    private static GraphicsText slimeInfo;

    private Button sellButton;
    private Button nextRoundButton;
    private static Rectangle background;

    /** 
     * Sets up UI background, buttons, images, and text
     * Implements the sell, next round, and slime (buy) buttons 
     * @param UIX         x-position of UI (top left)
     * @param UIwidth     width of UI
     * @param UIheight    height of UI
     * @param canvas      the canvasWindow it is being placed on
     */
    public UI (int UIX, int UIwidth, int UIheight, CanvasWindow canvas) {
        UI.canvas = canvas;
        this.UIwidth = UIwidth;
        slimeImageList = new ArrayList<Image>();
        slimeButtonList = new ArrayList<Button>();
        towerList = new ArrayList<Tower>();

        background= new Rectangle(UIX, 0, UIwidth, UIheight);
        background.setFillColor(FILL_COLOR);
        canvas.add(background);

        moneyText = new GraphicsText(GameHandler.getMoney() + "");
        moneyText.setFont(FontStyle.BOLD, TILE_SIZE / 2);
        moneyText.setCenter(background.getX() + TILE_SIZE * 1.1, TILE_SIZE);
        canvas.add(moneyText);

        livesText = new GraphicsText(GameHandler.getLives() + "");
        livesText.setFont(FontStyle.BOLD, TILE_SIZE / 2);
        livesText.setCenter(background.getX() + TILE_SIZE * 1.1, TILE_SIZE * 2);
        canvas.add(livesText);

        roundText = new GraphicsText("");
        roundText.setFont(FontStyle.BOLD, TILE_SIZE * .5);
        roundText.setCenter(background.getX() + TILE_SIZE * 1.1, TILE_SIZE * 16);
        canvas.add(roundText);

        sellButton = new Button("Sell");
        sellButton.setPosition(UIX + (TILE_SIZE * 8), 0);
        canvas.add(sellButton);

        nextRoundButton = new Button("Next Round");
        nextRoundButton.setPosition(UIX + (TILE_SIZE * 1.1), TILE_SIZE * 15);
        canvas.add(nextRoundButton);

        enemyInfoTitle = new GraphicsText("Enemy Info:");
        enemyInfoTitle.setFont(FontStyle.BOLD, TILE_SIZE * .5);
        enemyInfoTitle.setCenter(background.getCenter().getX(), TILE_SIZE * 6);
        canvas.add(enemyInfoTitle);

        slimeInfo = new GraphicsText("");
        slimeInfo.setFillColor(Color.WHITE);
        slimeInfo.setPosition(background.getCenter().getX() - TILE_SIZE, TILE_SIZE);
        canvas.add(slimeInfo); 

        GraphicsText clickInfo = new GraphicsText("Click image to see stats");
        clickInfo.setCenter(background.getCenter().getX(), TILE_SIZE*5);
        canvas.add(clickInfo);

        populateEnemyImages();
        populateSlimeButtonImages(UIX);

        sellButton.onClick(() -> {
            GameHandler.sell();
        });

        nextRoundButton.onClick(() -> {
            if(!GameHandler.getRoundRunning() && GameHandler.getDificulty() != -1){
                if(GameHandler.getDificulty() == 0){
                    GameHandler.changeMoney(10);
                }
                if(GameHandler.getDificulty() == 1){
                    GameHandler.changeMoney(5);
                }
                GameHandler.setCurWave(GameHandler.nextWave());
                GameHandler.setRoundrunning(true);
                GameHandler.getEnemyList().add(GameHandler.getCurWave().getNextEnemy());
            }
        });
    }
    
    /**
     * Displays the info of whichever slime is being hovered over in UI
     */
    public static void updateSlimeInfo(Point position) {
        GraphicsObject obj = canvas.getElementAt(position);
        if (obj == slimeImageList.get(0)) {
            slimeInfo.setText(GreenSlime.STATS);
        } else if (obj == slimeImageList.get(1)) {
            slimeInfo.setText(OrangeSlime.STATS);
        } else if (obj == slimeImageList.get(2)) {
            slimeInfo.setText(RedSlime.STATS);
        } else if (obj == slimeImageList.get(3)) {
            slimeInfo.setText(BlueSlime.STATS);
        } else if (obj == slimeImageList.get(4)) {
            slimeInfo.setText(YellowSlime.STATS);
        } else {
            slimeInfo.setText("");
        }
    }

    /** 
     * Creates a new button, adds it to the slime button list
     * @param name   the name of the button (aka the color of the slime)
     * @param x      the x position of the button
     */
    private void createButton(String name, double x) {
        Button newButton = new Button(name);
        if(colorList.contains(name)) {
            newButton.setCenter(x, TILE_SIZE * 3);
        }
        slimeButtonList.add(newButton);
        canvas.add(newButton);
    }

    /** 
     * Creates the images for the buttons and places them in correct location
     * @param colorName         the color of the slime the image is for
     * @param x                 the x position of the image
     */
    private void buttonImage(String colorName, int x) {
        Image newImage = new Image(colorName + ".png");
        newImage.setScale(.8);
        newImage.setMaxHeight(UIwidth / 4);
        newImage.setPosition(x, TILE_SIZE * 3);
        slimeImageList.add(newImage);
        canvas.add(newImage);
    }

    /**
     * Places enemy images on the UI
     */
    private void populateEnemyImages() {
        int num = 1;
        for(String enemyName: enemyStringList) {
            Image enemyImage = new Image (enemyName + ".png");
            enemyImage.setScale(.5);
            enemyImage.setMaxHeight(UIwidth / 4);
            enemyImage.setPosition(background.getX() - TILE_SIZE * .5, enemyInfoTitle.getY() + TILE_SIZE * 1.1 * (num - 1));
            
            GraphicsText enemyTitle = new GraphicsText(enemyName);
            enemyTitle.setFont(FontStyle.BOLD, TILE_SIZE*.4);
            enemyTitle.setPosition(background.getX() + TILE_SIZE*1.6, enemyInfoTitle.getY() + TILE_SIZE * num * 1.1);
            GraphicsText enemyText = null;
            switch(enemyName){
                case("WhiteChicken"):{
                    enemyText = new GraphicsText("Basic enemy");
                    break;
                }
                case("BrownChicken"):{
                    enemyText = new GraphicsText("Tougher enemy with more health");
                    break;
                }
                case("BlueChicken"):{
                    enemyText = new GraphicsText("This enemy regens and is imune to damage over time");
                    break;
                }
                case("YellowChicken"):{
                    enemyText = new GraphicsText("This enemy moves at double speed");
                    break;
                }
                case("BlackChicken"):{
                    enemyText = new GraphicsText("This armoured enemy resists flat damage from all attacks");
                    break;
                }
            }

            enemyText.setFont(FontStyle.PLAIN, TILE_SIZE * .3);
            enemyText.setPosition(background.getX() + TILE_SIZE * 2 - 15, enemyTitle.getY() + TILE_SIZE * .5);

            canvas.add(enemyImage);
            canvas.add(enemyTitle);
            canvas.add(enemyText);
            num += 1;
        }
    }

    private void populateSlimeButtonImages(int x) {
        int ticker = 0;
        for(String colorName: colorList) {
            buttonImage(colorName, x);
            createButton(colorName, slimeImageList.get(ticker).getCenter().getX());
            Button towerButton = slimeButtonList.get(ticker);
            towerButton.onClick(() -> {
                Tower tower = null;
                switch(colorName) {
                    case("Green"): {
                        tower = new GreenSlime(GameHandler.selectedTile());
                        break;
                    }
                    case("Yellow"): {
                        tower = new YellowSlime(GameHandler.selectedTile());
                        break;
                    }
                    case("Orange"): {
                        tower = new OrangeSlime(GameHandler.selectedTile());
                        break;
                    }
                    case("Red"): {
                        tower = new RedSlime(GameHandler.selectedTile());
                        break;
                    }
                    case ("Blue"): {
                        tower = new BlueSlime(GameHandler.selectedTile());
                        break;
                    }
                }
                if (GameHandler.selectedTile() != null) {
                    if(tower.canBuy() && GameHandler.returnTowerOnTile(GameHandler.selectedTile()) == null && 
                        GameHandler.selectedTile().getIsPath() == false && GameHandler.getDificulty() != -1){
                        GameHandler.changeMoney(-tower.returnCost());
                        towerList.add(tower);
                        GameHandler.addTower(tower);
                        tower.setImageCentered();
                        canvas.add(tower.getSprite());
                    }
                }
                
            });
            x += slimeImageList.get(0).getWidth();
            ticker += 1;
        }
    }

    /** 
     * Updates the text on the screen to reflect the updated/current game values 
     */
    public static void updateText() {
        livesText.setText("Lives : " + GameHandler.getLives());
        moneyText.setText("Money : " + GameHandler.getMoney());
        roundText.setText("Round : " + (GameHandler.getWave() + 1) + " / " + WaveHandler.getWaveListSize());
    }
}
