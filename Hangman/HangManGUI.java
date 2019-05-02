import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.awt.Container;
import java.lang.Object;
import java.util.*;

//Alexis

public class HangManGUI extends JFrame implements ActionListener {
   int frameWidth = 500;
   int frameHeight = 500;
   int bodyCount = 0;
   int letterCount = 0;
   HangMan hm;
   String word;
   
   public static void main(String[] args) {
      new HangManGUI("tooth");
   }
   
   public HangManGUI (String solveWord) {
      hm = new HangMan(solveWord);
      word = hm.getWord();
      System.out.println(word);
      setLayout(new BorderLayout());
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(3,10));
      JButton[] keyboardButtons = new JButton[30];
      String[] letters = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
         	"a", "s", "d", "f", "g", "h", "j", "k", "l", "", "",
            "z", "x", "c", "v", "b", "n", "m", "", ""};
      for (int i = 0; i < keyboardButtons.length; i++) {
         keyboardButtons[i] = new JButton(letters[i]);
         keyboardButtons[i].addActionListener(this);
         panel.add(keyboardButtons[i]);
      }
      add(panel, BorderLayout.SOUTH);
      setTitle("Hang Man");
      setSize(frameWidth,frameHeight);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      setLocationRelativeTo(null);
   }

   public void paint(Graphics g) {
      super.paint(g);
      g.drawLine(50,200,100,200);
      g.drawLine(75,70,75,200);
      g.drawLine(75,70,120,70);
      g.drawLine(120,70,120,100);
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
            g.drawString("Sorry, you lost, mate!", frameWidth/2, frameHeight/2);
            break;
      }
      int xpos1 = 150;
      int xpos2 = 170;
      int ypos = 200;   
      char[] solvingWord = hm.getCurrentWord();
      for (int i = 0; i < word.length(); i++) {
         g.drawLine(xpos1,ypos,xpos2,ypos);
         g.drawString(String.valueOf(solvingWord[i]), xpos1 + 8, ypos);  
         xpos1 += 60;
         xpos2 += 60;
      }
   }
      
   public void letter(boolean correct) {
      if (correct == false) {
         System.out.println(bodyCount);
         bodyCount ++;
      } else if (correct == true) {
         letterCount ++;
         System.out.println("Yay letter found");
      }
   }
      
   public void actionPerformed(ActionEvent e) {  
      System.out.println(e.getActionCommand());
      boolean check = checkLetter(e.getActionCommand(), word);
      JButton jb = (JButton) e.getSource();
      jb.setEnabled(false);
      letter(check);
      repaint(); 
   }
   
   public boolean checkLetter(String letter, String wrd) {
      if (wrd.contains(letter)) {
         System.out.println("Letter Found");
         hm.update(letter);
         return true;
      } else {
         System.out.println("Letter not found");
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