package Slime_Game;

/**
 * This class stores a path, and feeds it to gamehandler - it is set up for further expansion of the project
 */
public class GameMap {
    private String path;

    /**
     * stores the given path so gameHandler can turn it into a full Map
     * @param path a string form of the path
     */
    GameMap(String path) {
        this.path = path;
    }

    /**
     * returns the string value of the path
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Calculates and returns the total length of the path
     */
    public int getLength() {
        int toReturn = 0;
        for(Character c : path.toCharArray()) {
            if(Character.isDigit(c)) {
                toReturn += Character.getNumericValue(c);
            }
        }
        return toReturn;
    }    
}
