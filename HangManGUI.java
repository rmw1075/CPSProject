import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.awt.Container;
import java.lang.Object;
import java.util.*;

/**
 * A class to create the GUI for hangman
 * 5/6/2019
**/

public class HangManGUI extends JFrame implements ActionListener {
   //frame settings
   int frameWidth = 500;
   int frameHeight = 500;
   
   //counters
   int bodyCount = 0;
   int letterCount = 0;
   HangMan hm;
   String word;
   
   //colors for GUI
   Color buttonColor = new Color(209,179,255);
   Color manColor = new Color(255,199,57);
   Color lineColor = new Color(235,131,142);
   
   public HangManGUI (String solveWord) {
      word = solveWord;
   }

   public void play(){
      //create new game with word chosen by server
      int reply = JOptionPane.showConfirmDialog(null, "Want to play hangman?", "Play Hangman?", JOptionPane.YES_NO_OPTION);
      if (reply == JOptionPane.NO_OPTION) {
        return;
      }
      hm = new HangMan(word);
      word = hm.getWord();
      System.out.println(word);
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(3,10));
      
      //add keyboard
      JButton[] keyboardButtons = new JButton[30];
      String[] letters = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
         	"a", "s", "d", "f", "g", "h", "j", "k", "l", "", "",
            "z", "x", "c", "v", "b", "n", "m", "", ""};
      for (int i = 0; i < keyboardButtons.length; i++) {
         keyboardButtons[i] = new JButton(letters[i]);
         keyboardButtons[i].addActionListener(this);
         keyboardButtons[i].setBackground(buttonColor);
         panel.add(keyboardButtons[i]);
      }
      add(panel, BorderLayout.SOUTH);
      setTitle("Hang Man");
      setSize(frameWidth,frameHeight);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      setVisible(true);
      setLocationRelativeTo(null);
   }
   public void paint(Graphics g) {
      //paint the hook
      super.paint(g);
      g.drawLine(50,200,100,200);
      g.drawLine(75,70,75,200);
      g.drawLine(75,70,120,70);
      g.drawLine(120,70,120,100);
      
      //paint in the man if letter is incorrect
      g.setColor(manColor);
      switch (bodyCount) {
         case 1:
            g.drawOval(105,100,30,30);
            break;
         case 2:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            break;
         case 3:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            break;
         case 4:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            g.drawLine(120,130,130,150);
            break;
         case 5:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            g.drawLine(120,130,130,150);
            g.drawLine(120,175,110,195);
            break;
         case 6:
            g.drawOval(105,100,30,30);
            g.drawLine(120,130,120,175);
            g.drawLine(120,130,110,150);
            g.drawLine(120,130,130,150);
            g.drawLine(120,175,110,195);
            g.drawLine(120,175,130,195);
            JOptionPane.showMessageDialog(null, "Sorry, you lost, mate!");
            break;
      }
      
      //draw lines and letters
      int xpos1 = 150;
      int xpos2 = 170;
      int ypos = 200;   
      char[] solvingWord = hm.getCurrentWord();
      for (int i = 0; i < word.length(); i++) {
         g.setColor(lineColor);
         g.drawLine(xpos1,ypos,xpos2,ypos);
         g.setColor(Color.BLACK);
         g.drawString(String.valueOf(solvingWord[i]), xpos1 + 8, ypos);  
         xpos1 += 60;
         xpos2 += 60;
      }
   }
    
   //increase counters based on letter choice  
   public void letter(boolean correct) {
      if (correct == false) {
         bodyCount ++;
      } else if (correct == true) {
         letterCount ++;
      }
   }
   
   //disable used buttons
   public void actionPerformed(ActionEvent e) {  
      boolean check = checkLetter(e.getActionCommand(), word);
      JButton jb = (JButton) e.getSource();
      jb.setEnabled(false);
      letter(check);
      repaint();
      if(hm.checkWin()){
         JOptionPane.showMessageDialog(null, "You won!!!!");
         return;
      }
   }
   
   //is the letter in the word
   public boolean checkLetter(String letter, String wrd) {
      if (wrd.contains(letter)) {
         hm.update(letter);
         return true;
      } else {
         return false;
      }
   }
}
   
   
   // What needs to be done
   // 1. Add Set word as separate function so that it can be easily accessed here
   // 2. After word is set, start checking for correct word by
   // adding a function that will check if a letter being passed in
   // is within the word
   // 3. Update paint based on errors (When person pick wrong letter
   // body parts should begin to appear)
   // 4. Figure out next steps
   
   //