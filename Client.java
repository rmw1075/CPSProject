
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {
    JButton jb;
    JTextField jtf;
    JTextArea jta;
    String name;

    public Client() {
        setLayout(new BorderLayout());
        // String ip = JOptionPane.showInputDialog(null, "Enter IP address of
        // chatroom");
        // int server = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter socket
        // of chatroom"));
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setAutoscrolls(true);
        add(jta, BorderLayout.CENTER);
        JPanel jtfPanel = new JPanel();
        jtf = new JTextField(40);
        jtfPanel.add(jtf);
        JButton send = new JButton("Send");
        JMenuBar games = new JMenuBar();
        JMenu gamesMenu = new JMenu("Games");
        JMenuItem hangman = new JMenuItem("Play Hangman");
        gamesMenu.add(hangman);
        games.add(gamesMenu);
        setJMenuBar(games);
        jtfPanel.add(send);
        add(jtfPanel, BorderLayout.SOUTH);
        setTitle("Client Chat");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Socket s = null;
        try{
            name = InetAddress.getLocalHost().getHostName();
        }catch(UnknownHostException uhe){

        }
        try {
            int port = 2019;
            InetAddress address = Inet4Address.getLocalHost();
            System.out.println("Localhost: " + address.getHostAddress() + " Port: " + port);
            s = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        try {
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientListener listener = new ClientListener(oos);
        send.addActionListener(listener);
        hangman.addActionListener(listener);
        ReadThread readObjects = new ReadThread(ois);
        readObjects.run();
    }

    public class ClientListener implements ActionListener{
        private ObjectOutputStream oos;
        public ClientListener(ObjectOutputStream oos){
            this.oos = oos;
        }
        public void actionPerformed( ActionEvent ae){
            String command = ae.getActionCommand();
            if(command.equals("Send")){
                sendMessage();
            } else if(command.equals("Play Hangman")){
                sendRequest("Hangman");
            }
        } 
        public void sendMessage(){
            String message = jtf.getText();
            try{
                oos.flush();
                oos.writeObject(message);
                oos.flush();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }catch(NullPointerException ioe){
                ioe.printStackTrace();
            }
            jtf.setText("");
        }

        public void sendRequest(String game){
            Request req = new Request(name, game);
            try{
                oos.writeObject(req);
                oos.flush();
            }catch(IOException ioe){
                ioe.printStackTrace();
                System.out.println(ioe);
            }catch(NullPointerException ioe){
                ioe.printStackTrace();
            }
        }
    }


   
    public class ReadThread implements Runnable{
        private ObjectInputStream ois;
        public ReadThread(ObjectInputStream ois){
            this.ois = ois;
        }
        public void run(){
            while(true){
                try{
                    Object obj = ois.readObject();
                    if(obj instanceof String){
                        String message = (String) obj;
                        jta.append(message + "\n");
                    }else if(obj instanceof Request){
                    }
                }catch(ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }catch(IOException ioe){
                    jta.append("Server Shutdown, Goodbye");
                    break;
                }
            }
        }
    }

    public static void main(String[] args){
        new Client();
    }
}
