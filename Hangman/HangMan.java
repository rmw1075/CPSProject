import java.util.*;
import java.io.*;

public class HangMan
{   
    //Variables, will hold the words
    private ArrayList<String> wordList = new ArrayList<String>();
    private ArrayList<String> solveWord = new ArrayList<String>();
    private String word = "";
    final String DEFAULT = "WordList2.txt";

    //Generates the game
    public HangMan(String filename)
    {
        if(filename.equals(""))
        {
            String word = setWord("WordList2.txt");
            System.out.println(word);
            solveWord(word);
            System.exit(0);
            
        }
        else
        {
            String word = setWord(filename);
            System.out.println(word);
            System.exit(0);
        }

    }

    //Accepts a filename and builds an ArrayList with the words
    //Picks one of the words and returns it 
    public String setWord(String filename)
    {   
        try
        {
            File fl = new File(filename);
            Scanner scan = new Scanner(fl);

            while(scan.hasNext())
            {
                wordList.add(scan.next());
            }

            System.out.println(wordList);

            scan.close();
        }
        catch(FileNotFoundException fnf)
        {
            System.out.println(fnf);
        }

        int max = wordList.size() - 1;
        int min = 1;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;

        word = wordList.get(rand);


        return word;

    }//End of SetWord
    
    //Function that when called will start the solving of the game, if attempts are higher than the body count, 
    public void solveWord(String wrd)
    {
      for (char c : wrd.toCharArray())
      {
         String convert = Character.toString(c);
         solveWord.add(convert);
      }
      
      for(int i = 0; i < 6; i++)
      {
         System.out.print("Enter a letter ");
         Scanner input = new Scanner(System.in);
         String in = input.nextLine();
         
         
         checkLetter(in);
         
         
      }
      
      System.out.println(solveWord);
    }//End of solveWord
    
    public boolean checkLetter(String wrd)
    {
      if(solveWord.contains(wrd))
      {
         System.out.println("Letter Found");
         return true;
      }
      else
      {
         System.out.println("Letter not found");
         return false;
      }
    }
    

    public static void main(String[] args)
    {
        System.out.print("Enter filename for hangman words, or press enter for default word list: ");
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        new HangMan(in);
    }
}