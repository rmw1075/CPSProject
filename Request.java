import java.io.Serializable;

public class Request implements Serializable{
    private static final long serialVersionUID = 1L;
    String name = null;
    String game = null;

    public Request(String name, String game){
        this.name = name;
        this.game = game;
    }
}
