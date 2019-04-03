import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame implements ActionListener {
    JButton jb;
    JTextField jtf;
    JTextArea jta;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    String bozo = "clown";
    String name;

    public Client() {
        setLayout(new BorderLayout());
        //String ip = JOptionPane.showInputDialog(null, "Enter IP address of chatroom");
        //int server = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter socket of chatroom"));
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
        send.addActionListener(this);
        jtfPanel.add(send);
        add(jtfPanel, BorderLayout.SOUTH);
        
        setTitle("Client Chat");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        try {
            int port = 2019;
            InetAddress address = Inet4Address.getLocalHost();
            System.out.println("Localhost: " + address.getHostAddress() + " Port: " + port);
            Socket s = new Socket(address, port);
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
            bozo = "clown";
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
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
        readObjects.start();
    }


    public void actionPerformed( ActionEvent ae){
        String command = ae.getActionCommand();
        if(command.equals("Send")){
            sendMessage();
        }
    }

    public void sendMessage(){
        String message = jtf.getText();
        System.out.println(message);
        //bozo = _bozo;
        System.out.println(bozo);
        try{
            System.out.println(bozo);
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

    public static void main(String[] args){
        new Client();
    }
}
