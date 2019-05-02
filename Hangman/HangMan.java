import java.util.*;

public class HangMan
{   
   private ArrayList<String> solveWord = new ArrayList<String>();
   private char[] wordChar;
   private String word;

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

   public char[] getCurrentWord() {
      return wordChar;
   }

   public String getWord() {
      return word;
   }
    
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