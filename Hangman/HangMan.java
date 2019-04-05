import java.util.*;
import java.io.*;

public class HangMan
{   
    //Variables, will hold the words
    private ArrayList<String> wordList = new ArrayList<String>();
    private String word = "";
    final String DEFAULT = "WordList2.txt";

    //Generates the game
    public HangMan(String filename)
    {
        if(filename.equals(""))
        {
            System.out.println(setWord("WordList2.txt"));
        }
        else
        {
            System.out.println(setWord(filename));
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

        int max = wordList.size();
        int min = 1;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;

        word = wordList.get(rand);


        return word;

    }//End of SetWord

    public static void main(String[] args)
    {
        System.out.print("Enter filename for hangman words, or press enter for default word list: ");
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        new HangMan(in);
    }
}