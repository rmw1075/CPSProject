import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame implements ActionListener {
    private Vector<ClientHandler> users = new Vector<ClientHandler>();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JTextArea jta;

    public Server() throws IOException{
        setLayout(new BorderLayout());
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
        
        setTitle("Server Chat");
        setSize(900, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        String ip = null;
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        int port = 2019;
        ServerSocket ss = new ServerSocket(port);
        Socket s;
        jta.append(String.format("Server Up, Running On IP: %s PORT: %d", ip, port));
        while(true){
            s = ss.accept();
            System.out.println("Waiting for connection");
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());            
            jta.append("User connected\n");
            System.out.println("Conected");
            ClientHandler client = new ClientHandler( s,ois, oos);
            Thread t = new Thread(client);
            users.add(client);
            t.start();
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
        try{
            new Server();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    } 
}
