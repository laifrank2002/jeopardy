import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
/**
 * Write a description of class HighScore here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HighScore
{
    // constants
    private static final String fileLocation = "Data/highscore.data";
    private static final String SPLIT_MARKER = "@";
    private static final int NUMBER_OF_HIGHSCORES = 10;

    // instance variables - replace the example below with your own
    private int[] highScore = new int[NUMBER_OF_HIGHSCORES];
    private String[] name = new String[NUMBER_OF_HIGHSCORES];
    private BufferedReader inputFile;
    PrintWriter outputFile;

    /**
     * Constructor for objects of class HighScore
     */
    public HighScore()
    {
        // initialise instance variables
        getData();
    }

    /**
     * Returns this name.
     * 
     * @return     the name.
     */
    public String getName(int index)
    {
        return this.name[index];
    }

    /**
     * Returns this high score.
     * 
     * @return the high score.
     */
    public int getHighScore(int index)
    {
        return this.highScore[index];
    }

    public void recordHighScore(String name, int highScore)
    {
        boolean isEntered = false;
        try
        {
            outputFile = new PrintWriter (new FileWriter (fileLocation));
        }
        catch(IOException exception)
        {
        }
        for(int counter = 0; counter < 10; counter++)
        {
            if(highScore > this.highScore[counter]&&!isEntered)
            {
                outputFile.println(name + SPLIT_MARKER + highScore);
                counter = counter - 1;
            }
            else if(highScore == this.highScore[counter]&&!isEntered)
            {
            }

            
        }
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    private void getData()
    {
        int highScore = 0;
        boolean isExit = false;
        String lineOfText = "";
        // put your code here
        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(fileLocation));
            } // end of try
            catch(IOException exception)
            {
                System.out.println(exception);
            } // end of catch(IOException exception)
        }
        while(inputFile == null);
        for(int index = 0; index < NUMBER_OF_HIGHSCORES; index++)
        {
                try
                {
                    lineOfText = inputFile.readLine();
                } // end of try
                catch(IOException exception)
                {
                    System.out.println(exception);
                } // end of catch(IOException exception)
                if(lineOfText!= null&&lineOfText.contains(SPLIT_MARKER))
                {
                    // split name from high score
                    String[] solution = new String[2];
                    // split string to exclude " "
                    solution = lineOfText.split(SPLIT_MARKER);
                    this.name[index] = solution[0];
                    try
                    {
                        this.highScore[index] = Integer.parseInt(solution[1]);
                    }
                    catch(NumberFormatException exception)
                    {
                    }
                } // end of if(lineOfText != null)
        }
    }
}
