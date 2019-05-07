/**
 * A class to request to play game
 * @author Ryan, Alexis, Diego
 * @version 5/7/2019
**/
import java.io.Serializable;

public class Request implements Serializable{
    private static final long serialVersionUID = 1L;
    String name = null;
    String game = null;

    /**
     * Sends request for game
     * @peram name String 
     * @peram game String
    **/
    public Request(String name, String game){
        this.name = name;
        this.game = game;
    }
}
