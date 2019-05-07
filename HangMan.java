
package hangman;
/**
 * Hangman Game Skeleton
 * @author Ryan, Alexis, Diego
 * @version 05/07/2019
 */
import hangman.*;
import java.util.*;

/***
 * Hangman class, contains getter and setter for getting word, checking
 * win condition is true or false, and updating on based on word
 **/
public class HangMan {
   private ArrayList<String> solveWord = new ArrayList<String>();
   private char[] wordChar;
   private String word;

   /**
    *Hangman constructor
    *@param word sets the word to be used in the game
    **/
   public HangMan(String word) {
      this.word = word;
      wordChar = new char[word.length()];
      for (int a = 0; a < word.length(); a++) {
         char c = word.charAt(a);
         String ch = Character.toString(c);
         solveWord.add(ch);
         wordChar[a] = ' ';
      }
   }

   /**
    * Method that returns current word
    * @return returns char array with letters in current word
    **/
   public char[] getCurrentWord() {
      return wordChar;
   }
   
   /**
    * Method that checks if the player has won
    * @return boolean returns boolean with true if player won, and false if they lost
    **/
   public boolean checkWin(){
      String check = new String(wordChar);
      if(check.equals(word)){
         return true;
      } else {
         return false;
      }
   }
   
   /**
    * Method that returns the current word
    * @return return a string with the word that was set
    **/
   public String getWord() {
      return word;
   }
   
   /**
    * Method that updates the current letter location in char array
    * @param sc current letter type string from the character array
    **/ 
   public void update(String sc) {
      char c = sc.charAt(0);
      for (int i = 0; i < solveWord.size(); i++) {
         String s = solveWord.get(i);
         if (s.charAt(0) == c) {
            wordChar[i] = c;
         }
      }
   }
}