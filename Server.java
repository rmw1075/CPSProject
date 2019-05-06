
import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
public class Server extends JFrame implements ActionListener {
    private Vector<ClientHandler> users = new Vector<ClientHandler>();
    private JTextArea jta;
    private String filename = "WordList1.txt";
    public Server() throws IOException {
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        String ip = null;
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        int port = 2019;
        ServerSocket ss = new ServerSocket(port);
        Socket s;
        jta.append(String.format("Server Up, Running On IP: %s PORT: %d\n", ip, port));
        System.out.println("Test");
        while (true) {
            s = ss.accept();
            String userName = InetAddress.getLocalHost().getHostName();
            jta.append(String.format("User connected: %s\n", userName));
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ClientHandler client = new ClientHandler(s, userName, oos, ois);
            users.add(client);
            Thread t = new Thread(client);
            for (ClientHandler mc : users) {
                oos.writeObject(String.format("%s has entered the server", userName));
                oos.flush();
            }
            t.start();
        }
    }

    public void actionPerformed ( ActionEvent ae ) {
        String command = ae.getActionCommand();
        if (command.equals("Exit")) {
            System.exit(1);
        }
    }

    /**
     * ClientHandler class
     */
    public class ClientHandler implements Runnable {
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
        public ClientHandler(Socket s, String name, ObjectOutputStream oos, ObjectInputStream ois) {
            this.s = s;
            this.name = name;
            this.oos = oos;
            this.ois = ois;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof String) {
                        String s = (String) obj;
                        if (s.length() >= 7 && (s.substring(0, 7)).equals("COMMAND")) {
                            try {
                                oos.writeObject(getUsers());
                                oos.flush();
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            } catch (NullPointerException npe) {
                                npe.printStackTrace();
                            }
                        } else {
                            String message = String.format("%s: %s", this.name, (String) obj);
                            jta.append(message);
                            for (ClientHandler mc : users) {
                                oos.writeObject(message);
                            }  
                        }  
                    } else if (obj instanceof Request) {
                        Request gameRequest = (Request) obj;
                        jta.append(String.format("%s has requested to play %s, sending game to clients.\n", gameRequest.name, gameRequest.game));
                        String word = setWord(filename);
                        System.out.println("Word chosen is: " + word);                      
                        for(ClientHandler mc : users){
                            HangManGUI hang = new HangManGUI(word);
                            oos.writeObject(hang);
                        } 
                    }
                } catch (IOException ioe) { 
                } catch (ClassNotFoundException cnfe) { }
            }
        }

        public ArrayList<String> getUsers(){
            ArrayList<String> usersStr = new ArrayList<String>();
            for(int a = 0; a < users.size(); a++){
                usersStr.add(users.get(a).name);
            }
            return usersStr;
        }

        public String setWord (String filename) {   
            ArrayList<String> wordList = new ArrayList<String>();
            try {
                File fl = new File(filename);
                Scanner scan = new Scanner(fl);
                while(scan.hasNext()){
                    String wrd = scan.next();
                    //System.out.println(wrd);
                    if(wrd.length() < 2 || wrd.length() > 6) {
                    //Skip words greater than 6 letters long and shorter than 2 letters
                        continue;
                    } else {
                        wordList.add(wrd);
                    }
                //wordList.add(scan.next());
                }
                scan.close();
            } catch(FileNotFoundException fnf) { }
            int max = wordList.size() - 1;
            int min = 1;
            int range = max - min + 1;
            int rand = (int)(Math.random() * range) + min;
            String chosenWord = wordList.get(rand);
            return chosenWord;
        }//End of SetWord
    }

    /**
     * MAIN METHOD
     * @param args Arguments
     */
    public static void main(String[] args) {
        try {
            new Server();
        } catch(IOException ioe) { }
    } 
}
