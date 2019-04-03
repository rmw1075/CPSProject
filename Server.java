import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame implements ActionListener{
    private static final long serialVersionUID = -4280936133506383901L;
    private Vector<ClientHandler> users = new Vector<>();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JTextArea jta;

    public Server(){
        setLayout(new BorderLayout());
        setTitle("Server Chat");
        setSize(900, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton exit = new JButton("Exit");
        buttons.add(exit);
        add(buttons, BorderLayout.SOUTH);
        exit.addActionListener(this);
        panel.setLayout(new BorderLayout());
        jta = new JTextArea();
        panel.add(jta, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        revalidate();
        jta.append("Server Socket Open\n");
        ServerSocket ss = null;;
        try{
            ss = new ServerSocket(2019);
            Socket s = null;
            while(true){
                s = ss.accept();
                ois = new ObjectInputStream(s.getInputStream());
                oos = new ObjectOutputStream(s.getOutputStream());
                ClientHandler client = new ClientHandler( s,ois, oos);
                users.add(client);
                client.run();
            }
        }catch(IOException ioe){
        }finally{
            try{
                jta.append("Server Socket Closed\n");
                ss.close();  
            }catch(IOException ioe){
                System.out.println(ioe);
            } 
        }
    }

    public void actionPerformed( ActionEvent ae){
        String command = ae.getActionCommand();
        if (command.equals("Exit")){
            System.exit(1);
        }
    }

    /**
     * ClientHandler class
     */
    public class ClientHandler implements Runnable{
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private String name = null;
        private Socket s;

        /**
         * Constructor for Client Handler
         * @param s Socket
         * @param ois ObjectInputStream
         * @param oos ObjectOutputStream
         */
        public ClientHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos){
            this.ois = ois;
            this.oos = oos;
            this.s = s;
        }

        @Override
        public void run(){
            while(true){
                try{
                    Object obj = ois.readObject();
                    if (obj instanceof String){
                        String message = (String) obj;
                        jta.append(message);
                        for(ClientHandler mc : users){
                            oos.writeObject(message);
                        }
                    }else if(obj instanceof Request){
                        for(ClientHandler mc : users){
                            oos.writeObject(obj);
                        }
                    }
                }catch(IOException ioe){
                    System.out.println(ioe);
                }catch(ClassNotFoundException cnfe){
                    System.out.println(cnfe);
                }
            }
        }
    }

    /**
     * MAIN METHOD
     * @param args Arguments
     */
    public static void main(String[] args){
        new Server();
    } 
}
