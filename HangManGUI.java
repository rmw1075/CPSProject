import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.awt.Container;
import java.lang.Object;

//Alexis

public class HangManGUI extends JFrame implements ActionListener {
   int frameWidth = 500;
   int frameHeight = 500;
   int bodyCount = 0;
   String word;
   
   public static void main(String [] args) {
      new HangManGUI("");
   }
   
   public HangManGUI (String _word) {
      word = _word;
      JFrame frame = new JFrame("Hangman");
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(3,10));
      
      //keyboard
      JButton[] keyboardButtons = new JButton[30];
		String[] letters = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
				"a", "s", "d", "f", "g", "h", "j", "k", "l", "", "",
            "z", "x", "c", "v", "b", "n", "m", "", ""};

		for (int i = 0; i < keyboardButtons.length; i++) {
			keyboardButtons[i] = new JButton(letters[i]);
			keyboardButtons[i].addActionListener(this);
			panel.add(keyboardButtons[i]);
		} 
      
      repaint();
      
      add(panel, BorderLayout.SOUTH);
      //set up J Frame
      setTitle("Hang Man");
      setSize(frameWidth,frameHeight);
      //pack();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      setLocationRelativeTo(null);
   }
   
     //method to paint the icon and draw finish line
      public void paint(Graphics g) {
         super.paint(g);
         //draw gallow
         //base
         g.drawLine(50,200,100,200);
         //stem
         g.drawLine(75,70,75,200);
         //top
         g.drawLine(75,70,120,70);
         //hook
         g.drawLine(120,70,120,100);
         
         //draw man
         switch (bodyCount) {
         
            //head
            case 1:
            g.drawOval(105,100,30,30);
            break;
            
            //body
            case 2:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            break;
            
            //arm1
            case 3:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            break;
            
            //arm2
            case 4:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            g.drawLine(120,130,130,150);
            break;
            
            //leg1
            case 5:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            g.drawLine(120,130,130,150);
            g.drawLine(120,175,110,195);
            break;
            
            //leg2 - last body part
            case 6:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            g.drawLine(120,130,130,150);
            g.drawLine(120,175,110,195);
            g.drawLine(120,175,130,195);
            g.drawString("Sorry, you lost!", 300, 300);
            break;
         }
         
         //draw lines for word
         for(int i = 0; i < word.length(); i++) {
            int xpos1 = 150;
            int xpos2 = 170;
            
            g.drawLine(xpos1,200,xpos2,200);
            
            xpos1 += 30;
            xpos2 += 30;
         }
         
      }
      
      public void letter(boolean correct) {
         if (correct = false) {
            bodyCount ++;
         }
         if (correct = true) {
            //add letter to its place
         }
      }
      
      public void actionPerformed(ActionEvent e) {  
         //send Diego letter
      }

	}