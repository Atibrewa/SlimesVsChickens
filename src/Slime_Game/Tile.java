package Slime_Game;

import java.awt.Color;

import Slime_Game.Towers.Tower;
import Graphics.GraphicsGroup;
import Graphics.GraphicsObject;
import Graphics.Point;
import Graphics.Rectangle;

/**
 * @author Atibrewa
 * @author bean-b
 * @author Mkleit
 *  
 * Creates a tile and handles its functions
 */
public class Tile extends GraphicsGroup {
    public static final Color PATH_COLOR = new Color(161, 166, 149);
    public static final Color BACKGROUND_TILE = new Color(65, 94, 38);
    public static final Color SELECTED_TILE = new Color(200, 207, 21);
    // xcord / ycord is index in tile system and NOT the pixel position, which is x,y
    private int xCord;
    private int yCord;
    private Rectangle tileShape;
    private boolean isSelected;
    private boolean isPath;
    private Tower tower;

    /**
     * Creates a rectangle for the tile 
     * handles values such as wif the tile is selected, if there is a tower on it, if it is a path etc
     * 
     * @param x       x-position on canvas
     * @param y       y-position on canvas
     * @param width   width of tile
     * @param height  height of tile
     * @param xCord   x coordinate in index system of grid
     * @param yCord   y coordinate in index system of grid
     */
    public Tile (double x, double y, double width, double height, int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;

        isSelected = false;
        isPath = false;
        tower = null;

        tileShape = new Rectangle(x, y, width, height);
        tileShape.setFillColor(BACKGROUND_TILE);
        tileShape.setStrokeColor(Color.BLACK);
        tileShape.setStrokeWidth(3);
        this.add(tileShape);
    }

    /**
     * changes the colour of the tile to show it is selected and sets
     * value of isSelected to true
     */
    public void select() {
        tileShape.setFillColor(SELECTED_TILE);
        isSelected = true;
    }

    /**
     * changes tile colour back to normal and sets isSelected to false
     */
    public void deselect() {
        tileShape.setFillColor(BACKGROUND_TILE);
        isSelected = false;
    }

    /**
     * sets the color of the tile to the path colour 
     * and assigns value of isPath to be true
     * @param isPath true if the tile is part of the path that enemies travel on
     */
    public void setPath(boolean isPath) {
        tileShape.setFillColor(PATH_COLOR);
        this.isPath = isPath;
    }

    /**
     * sets the value of the var tower in Tile to the given tower
     * @param tower
     */
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    /**
     * adds the given object to this graphicsGroup (such as a tower or enemy)
     * @param graphicsObject
     */
    public void addToGraphicsGroup(GraphicsObject graphicsObject) {
        this.add(graphicsObject);
    }

    /**
     * removes the given object from this graphicsGroup (such as a tower or enemy)
     * @param graphicsObject
     */
    public void removeFromGraphicsGroup(GraphicsObject graphicsObject) {
        this.remove(graphicsObject);
    }

    /**
     * returns true if the tile is currently selected
     * @return
     */
    public boolean getTileSelected() {
        return isSelected;
    }

    /**
     * returns y-coordinate in grid
     * @return
     */
    public int getYCord() {
        return yCord;
    }

    /**
     * returns x coordinate in grid
     * @return
     */
    public int getXCord() {
        return xCord;
    }

    /**
     * returns true if it is a path
     * @return
     */
    public boolean getIsPath() {
        return isPath;
    }

    /**
     * returns the tower placed on it, if any
     * @return
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * returns x-y position on canvas
     * @return
     */
    public Point getPixelCenter() {
        return tileShape.getCenter();
    }

    /**
     * returns the base rectangle shape that is the tile
     * @return
     */
    public Rectangle getRectangle() {
        return tileShape;
    }
}
