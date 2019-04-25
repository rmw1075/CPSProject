import java.util.*;
import java.io.*;
import java.io.Serializable;
public class HangMan implements Serializable
{   
    //Variables, will hold the words
    private ArrayList<String> solveWord = new ArrayList<String>();
    private String word = "";
    private String uncovered = "";

    //Generates the game
    public HangMan(String word)
    {
      this.word = word;
    }
    
    public void start(){
      new HangManGUI(word);
      solveWord(word);
    }
    //Function that when called will start the solving of the game, if attempts are higher than the body count, 
    public void solveWord(String wrd)
    {
      String hide = "";      
      for (char c: wrd.toCharArray())
      {
        String ch = Character.toString(c);
        hide = hide + "-";
        solveWord.add(ch);
      }

      System.out.println(hide);
      
      for(int i = 0; i < 6; i++)
      {
         System.out.println("Enter a letter ");
         Scanner input = new Scanner(System.in);
         String in = input.nextLine();
         checkLetter(in);
      }
      
      System.out.println(solveWord);
    }//End of solveWord
    
    //Checks if the letter passed in is inside of the chosen word
    public boolean checkLetter(String wrd)
    {

      if(solveWord.contains(wrd))
      {
         uncovered = "";
         System.out.println("Letter Found");
         for (int i = 0; i<word.length(); i++)
         {
            if(solveWord.get(i) == wrd)
            {
                uncovered = uncovered + wrd;
            }
            else
            {
                uncovered = uncovered + "-";
            }

         }
         System.out.println(uncovered);
         return true;
      }
      else
      {
         System.out.println("Letter not found");
         System.out.println(uncovered);
         return false;
      }

      
    }//End of checkLetter
}