import java.util.*;
import java.io.*;

public class HangMan
{   
    //Variables, will hold the words
   private ArrayList<String> solveWord = new ArrayList<String>();
   private ArrayList<String> wordList = new ArrayList<String>();
   private String word = "";
   
    //Generates the game
    // public HangMan(String filename)
//     {
//         if(filename.equals(""))
//         {
//             String word = setWord("WordList2.txt");
//             new HangManGUI(word);
//             System.out.println(word);
//             solveWord(word);
//             System.exit(0);
//             
//         }
//         else
//         {
//             String word = setWord(filename);
//             System.out.println(word);
//             System.exit(0);
//         }
// 
//     }
    
   public HangMan()
   {
      word = setWord("WordList2.txt");
      for (char c: word.toCharArray())
      {
         String ch = Character.toString(c);
        // hide = hide + "-";
         solveWord.add(ch);
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
    
    //Function that when called will return the word set
   public String getWord()
   {
      return word;
   }
    
    //Function that when called will start the solving of the game, if attempts are higher than the body count, 
    // public void solveWord(String wrd)
//     {
//       // String hide = "";      
//       for (char c: wrd.toCharArray())
//       {
//         String ch = Character.toString(c);
//         // hide = hide + "-";
//         solveWord.add(ch);
//       }
      

      // System.out.println(hide);
      
      // for(int i = 0; i < 6; i++)
//       {
//          System.out.println("Enter a letter ");
//          Scanner input = new Scanner(System.in);
//          String in = input.nextLine();
//          checkLetter(in);
//       }
      
      // System.out.println(solveWord);
    //End of solveWord
    
    //Checks if the letter passed in is inside of the chosen word
   // public boolean checkLetter(String wrd)
//    {
//    
//       if(solveWord.contains(wrd))
//       {
//          uncovered = "";
//          System.out.println("Letter Found");
//          for (int i = 0; i<word.length(); i++)
//          {
//             if(solveWord.get(i) == wrd)
//             {
//                uncovered = uncovered + wrd;
//             }
//             else
//             {
//                uncovered = uncovered + "-";
//             }
//          
//          }
//          System.out.println(uncovered);
//          return true;
//       }
//       else
//       {
//          System.out.println("Letter not found");
//          System.out.println(uncovered);
//          return false;
//       }
   
      
   //}//End of checkLetter
    

    // public static void main(String[] args)
//     {
//         System.out.print("Enter filename for hangman words, or press enter for default word list: ");
//         Scanner input = new Scanner(System.in);
//         String in = input.nextLine();
//         // new HangMan(in);
//         //new HangMan(in);
//     }
}