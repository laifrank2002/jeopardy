import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Write a description of class Reader here.
 * 
 * @author William Wang
 * @version 1.0  2018-05-30
 */
public class Reader
{
    // instance variables - replace the example below with your own
    private static final int CATEGORY_SIZE = 5;
    private static final int QUESTION_SIZE = 5;
    private static String categoryFileName = "Data/categories.txt";
    private static int answers;
    private String[] categories;

    public Reader()
    {
        categories = new String[getNumberOfCategories()];
        answers = 5;
    }

    /**
     * Constructor for objects of class Reader
     */
    public int getNumberOfCategories()
    {
        int counter = 0;
        BufferedReader inputFile = null;
        String lineOfText = "";
        String fileName = "";
        boolean isExit = false;
        
        // initialise instance variables
        BufferedReader console = new BufferedReader (new InputStreamReader (System.in));

        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader (categoryFileName));
            }
            catch(IOException exception)
            {
                System.out.println(exception);
            }
        }
        while(inputFile == null);
        System.out.println("Here's the data below");
        System.out.println("-------------------------");
        do
        {
            try
            {
                lineOfText = inputFile.readLine();
            }
            catch(IOException exception)
            {
                System.out.println(exception);
            }
            if(lineOfText!=null)
            {
                System.out.println(lineOfText);
                counter = counter + 1;
            }
            else
            {
                isExit = true;
            }
        }
        while(!isExit);
        System.out.println("----------------------");
        return counter;
    }
    
    public String[] getCategories()
    {
        int counter = 0;
        BufferedReader inputFile = null;
        String lineOfText = "";
        String fileName = "";
        boolean isExit = false;
        
        // initialise instance variables
        BufferedReader console = new BufferedReader (new InputStreamReader (System.in));

        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader (categoryFileName));
            }
            catch(IOException exception)
            {
                System.out.println(exception);
            }
        }
        while(inputFile == null);
        System.out.println("Here's the data below");
        System.out.println("-------------------------");
        do
        {
            try
            {
                lineOfText = inputFile.readLine();
            }
            catch(IOException exception)
            {
                System.out.println(exception);
            }
            if(lineOfText!=null)
            {
                System.out.println(lineOfText);
                
                this.categories[counter] = lineOfText;
                counter = counter + 1;
            }
            else
            {
                isExit = true;
            }
        }
        while(!isExit);
        System.out.println("----------------------");
        return this.categories;
    }

            
}
