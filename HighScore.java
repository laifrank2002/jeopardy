import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
/**
 * Records the 10 highest scores.
 * 
 * @author William Wang
 * @version 1.0 2018.06.07
 */
public class HighScore
{
    // constants
    private static final String fileLocation = "Data/highscore.data";
    private static final String SPLIT_MARKER = "@";
    private static final int NUMBER_OF_HIGHSCORES = 10;
    private static final int NUMBER_OF_OLD_SCORES = 9;

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

    /**
     * Records the high score.
     * 
     * @param name the name of the player; must not be <code>null</code>.
     * @param highScore the score of the player; must be non-negative.
     */
    public void recordHighScore(String name, int highScore)
    {
        boolean isEntered = false;
        if(name != null && highScore >= 0)
        {
            try
            {
                outputFile = new PrintWriter (new FileWriter (fileLocation));
            }
            catch(IOException exception)
            {
            }
            for(int counter = 0; counter < NUMBER_OF_OLD_SCORES; counter++)
            {
                if(highScore > this.highScore[counter]&&!isEntered)
                {
                    outputFile.println(name + SPLIT_MARKER + highScore);
                    isEntered = true;
                    counter = counter - 1;
                }
                else if(highScore == this.highScore[counter]&&!isEntered)
                {
                    outputFile.println(this.name[counter] + SPLIT_MARKER + this.highScore[counter]);
                    outputFile.println(name + SPLIT_MARKER + highScore);
                    isEntered = true;
                }
                else
                {
                    outputFile.println(this.name[counter] + SPLIT_MARKER + this.highScore[counter]);
                }
            }
        }
        outputFile.close();
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
