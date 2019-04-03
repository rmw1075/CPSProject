import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame implements ActionListener {
    private static final long serialVersionUID = -8195823990683342026L;
    JButton jb = null;
    JTextField jtf = null;
    JTextArea jta = null;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    String name = null;

    public Client() {
        
        JPanel panel = new JPanel();
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setAutoscrolls(true);
        panel.add(jta);
        add(panel, BorderLayout.CENTER);
        JPanel jtfPanel = new JPanel();
        jtf = new JTextField(40);
        jtfPanel.add(jtf);
        JButton send = new JButton("Send");
        jtfPanel.add(send);
        add(jtfPanel, BorderLayout.SOUTH);
        pack();
        //String ip = JOptionPane.showInputDialog(null, "Enter IP address of chatroom");
        //int server = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter socket of chatroom"));
        try {
            int port = 2019;
            InetAddress address = Inet4Address.getLocalHost();
            Socket s = new Socket(address, port);
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread readObjects = new Thread(new Runnable(){
            @Override
            public void run() {
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
                    }
                }
            }
        });
        
        setLayout(new BorderLayout());
        setTitle("Client Chat");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        
        readObjects.start();
    }


    public void actionPerformed( ActionEvent ae){
        String command = ae.getActionCommand();
        if(command.equals("Send")){
            sendMessage();
        }
    }

    private void sendMessage(){
        String message = jtf.getText();
        jtf.setText("");
        try{
            oos.writeObject(message);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Client();
    }
}
