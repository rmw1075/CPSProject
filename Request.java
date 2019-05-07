/**
 * A class to request to play game
 * @author Ryan, Alexis, Diego
 * @version 5/7/2019
**/
import java.io.Serializable;

/**
 * Request class holds player name and game name
 * @param none
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    String name = null;
    String game = null;

    /**
     * Sends request for game
     * @param name String 
     * @param game String
    **/
    public Request(String name, String game) {
        this.name = name;
        this.game = game;
    }
}
