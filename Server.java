import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;

public class Server{
    private Vector<ClientHandler> users = new Vector<>();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Server(){
        try{
            ServerSocket ss = new ServerSocket(2019);
            Socket s = null;
            while(true){
                s = ss.accept();
                ois = new ObjectInputStream(s.getInputStream());
                oos = new ObjectOutputStream(s.getOutputStream());
                ClientHandler client = new ClientHandler(ois, oos);
            }
        }catch(IOException ioe){
        }
    }

    public class ClientHandler implements Runnable{
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private String name = null;
        private Chat chat = null;
        public ClientHandler(ObjectInputStream _ois, ObjectOutputStream _oos){
            ois = _ois;
            oos = _oos;
            chat = new Chat(ois, oos);
        }

        public void run(){
            try{
                while(true){
                    Object obj = ois.readObject();
                }
            }catch(IOException ioe){
            }catch(ClassNotFoundException cnfe){
            }
            
        }
    }

    public class Chat extends JFrame{
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private JTextArea jta;

        public Chat(ObjectInputStream _ois, ObjectOutputStream _oos){
            ois = _ois;
            oos = _oos;
            setTitle("Chat");
            setSize(900, 900);
            setLocationRelativeTo(null);
            setVisible(true);

            jta = new JTextArea();
        }

        public void write(Object obj){
            String str = (String) obj;
            jta.append(str + "\n");
        }
    }
    public static void main(String[] args){
        new Server();
    } 
}
