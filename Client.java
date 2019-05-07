import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;


/**
 *Client class, contains client constructor, extends JFrame
 */
public class Client extends JFrame {
   JButton jb;
   JTextField jtf;
   JTextArea jta;
   String name;
   Color buttonColor = new Color(209,179,255);
   Color dropColor = new Color(235,224,255);
   Color manColor = new Color(255,199,57);
   Color lineColor = new Color(235,131,142);
   Color backgroundColor = new Color(217,252,245);
   
   /**
    * Client constructor, sets up the GUI and connects to server
    * @param none
    * @return none
    **/
   public Client() {
      setLayout(new BorderLayout());
      String ip = JOptionPane.showInputDialog(null, "Enter IP address of chatroom");
      int server = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter socket of chatroom"));
      jta = new JTextArea();
      jta.setBackground(backgroundColor);
      jta.setEditable(false);
      jta.setLineWrap(true);
      jta.setAutoscrolls(true);
      add(jta, BorderLayout.CENTER);
      JPanel jtfPanel = new JPanel();
      jtf = new JTextField(40);
      jtfPanel.add(jtf);
      JButton send = new JButton("Send");
      send.setBackground(buttonColor);
      JMenuBar games = new JMenuBar();
      games.setBackground(buttonColor);
      JMenu gamesMenu = new JMenu("Games");
      JMenuItem hangman = new JMenuItem("Play Hangman");
      hangman.setBackground(dropColor);
      JMenuItem getUsers = new JMenuItem("Users Online");
      getUsers.setBackground(buttonColor);
      
      gamesMenu.add(hangman);
      games.add(gamesMenu);
      games.add(getUsers);
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
      try {
         name = InetAddress.getLocalHost().getHostName();
      } catch(UnknownHostException uhe) {
      
      }
      try {
         int port = 2019;
         //InetAddress address = Inet4Address.getLocalHost();
         //System.out.println("Localhost: " + address.getHostAddress() + " Port: " + port);
         s = new Socket(ip, server);
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
      getUsers.addActionListener(listener);
      send.addActionListener(listener);
      hangman.addActionListener(listener);
      ReadThread readObjects = new ReadThread(ois);
      readObjects.run();
   }
   
   /**
    * ClientListener class, implements ActionListener
    **/
   public class ClientListener implements ActionListener {
      private ObjectOutputStream oos;
      public ClientListener(ObjectOutputStream oos) {
         this.oos = oos;
      }
      public void actionPerformed( ActionEvent ae) {
         String command = ae.getActionCommand();
         if(command.equals("Send")){
            sendMessage();
         } else if (command.equals("Play Hangman")) {
            sendRequest("Hangman");
         } else if (command.equals("Users Online")) {
            seeUsers();
         }
      } 
      /**
       * Method that sends message to server
       * @param none
       * @return null/void
       */
      public void sendMessage() {
         String message = jtf.getText();
         try {
            oos.writeObject(message);
            oos.flush();
         } catch (IOException ioe) {
            ioe.printStackTrace();
         } catch (NullPointerException ioe) {
            ioe.printStackTrace();
         }
         jtf.setText("");
      }
      
      /**
       * Method that shows the current users connected to the server
       * @param none
       * @return null/void
       */
      public void seeUsers() {
         String st = "COMMAND-GET_USERS";
         try {
            oos.writeObject(st);
            oos.flush();
         } catch (IOException ioe) {
            ioe.printStackTrace();
         } catch (NullPointerException npe) {
            npe.printStackTrace();
         }
      }
      
      /**
       * Method that sends a request to play the hangman game
       * @param game string with the word
       * @return null/void
       */
      public void sendRequest(String game) {
         Request req = new Request(name, game);
         try {
            oos.writeObject(req);
            oos.flush();
         } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
         } catch(NullPointerException ioe) {
            ioe.printStackTrace();
         }
      }
   }
   
   /**
    * Class that reads a thread, implements runnable
    */
   public class ReadThread implements Runnable {
      private ObjectInputStream ois;
      public ReadThread(ObjectInputStream ois) {
         this.ois = ois;
      }
      public void run() {
         while (true) {
            try {
               Object obj = ois.readObject();
               if (obj instanceof String) {
                  String message = (String) obj;
                  jta.append(message + "\n");
               } else if(obj instanceof ArrayList){
                  ArrayList<String> users = (ArrayList<String>) obj;
                  showUsers(users);
               } else {
                  HangManGUI hang = (HangManGUI) obj;
                  hang.play();
               }
            } catch (ClassNotFoundException cnfe) {
            } catch (IOException ioe) {
               jta.append("Server Shutdown, Goodbye");
               break;
            }
         }
      }
      
      /**
       * Method that shows the users 
       * @param users Arraylist of type string with user information
       * @return void/null
       **/
      public void showUsers(ArrayList<String> users){
         JFrame jf = new JFrame();
         JPanel jp = new JPanel();
         JTextArea jta = new JTextArea();
         jta.setEditable(false);
         for(int a = 0; a < users.size(); a++){
            jta.append(users.get(a) + "\n");
         }
         jp.add(jta);
         jf.add(jp);
         jf.setTitle("Active Users");
         jf.setSize(300, 300);
         jf.setLocationRelativeTo(null);
         jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         jf.setVisible(true);
      }
   }
   
   /**
    * Main method, calls the client and runs
    * @param args arguments from the commandline, type string array
    * @return null/void
    **/
   public static void main(String[] args){
      new Client();
   }
}
