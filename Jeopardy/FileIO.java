import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Write a description of class FileIO here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FileIO
{
    // class constants
    private static final int CATEGORY_COUNT = 6;
    private static final int QUESTION_COUNT = 5;
    private static final int ANSWER_COUNT = 6;
    private static final String categoryFileName = "Data/categories.txt";
    private static final String fileLocationPrefix = "Data/";
    private static final String MARKER_PREFIX = "\\";
    private static final String CORRECT_MARKER = "*";

    // instance fields
    private static String[][] answers;
    private static String[] categories;
    private static String[] categoryFileNames;
    private static String[][] correctResponse;
    private static int numberOfCategories;
    private static String[][][] questions;

    /**
     * Constructor for objects of class FileIO
     */
    public FileIO()
    {
        // initialise console buffered reader
        BufferedReader console = new BufferedReader (new InputStreamReader (System.in));
        // read data from files
        getData();
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void getData()
    {
        // put your code here
        categories = new String[CATEGORY_COUNT];
        categoryFileNames = new String[CATEGORY_COUNT];
        int counter = 0;
        BufferedReader inputFile = null;
        String lineOfText = "";
        String fileName = "";
        boolean isExit = false;

        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(categoryFileName));
            }
            catch(IOException exception)
            {
                System.out.println(exception);
            }
        }
        while(inputFile == null);
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
                // insert category into array
                categories[counter] = lineOfText;
                // convert category to filename
                fileName = categories[counter].toLowerCase()+".txt";
                // insert file name into array
                categoryFileNames[counter] = fileLocationPrefix + fileName;
                counter = counter + 1;
            }
            else
            {
                isExit = true;
            }
        }
        while(!isExit);
        numberOfCategories = counter;
        System.out.println("----------------------");

        // get answers and questions

        answers = new String[CATEGORY_COUNT][ANSWER_COUNT];
        questions = new String[CATEGORY_COUNT][ANSWER_COUNT][QUESTION_COUNT];
        correctResponse = new String[CATEGORY_COUNT][ANSWER_COUNT];
        for(int category = 0; category<CATEGORY_COUNT; category++)
        {
            // reset answer and question count
            int answerCount = 0;
            int questionCount = 0;
            // reset exit condition
            isExit = false;
            do
            {
                try
                {
                    inputFile = new BufferedReader (new FileReader(categoryFileNames[category]));
                    System.out.println("Reading file: " + categoryFileNames[category]);
                }
                catch(IOException exception)
                {
                    System.out.println(exception);
                }
            }
            while(inputFile == null);
            System.out.println("Here's the answers and questions below");
            System.out.println("-------------------------");
            do
            {
                try
                {
                    lineOfText = inputFile.readLine();
                } // end of try
                catch(IOException exception)
                {
                    System.out.println(exception);
                } // end of catch(IOException exception)
                if(lineOfText!=null)
                {
                    // determine whether line of text is question or answer
                    if(lineOfText.contains("."))
                    {
                        if(answerCount == 0 && questionCount == 0)
                        {
                            // do not increment answer count if it is the first time reading an answer
                        } // end of if(answerCount == 0 && questionCount == 0)
                        else
                        {
                            answerCount = answerCount + 1;
                        }
                        // insert answer into array
                        answers[category][answerCount] = lineOfText;
                        // reset question count to 0
                        questionCount = 0;
                    }
                    else if(lineOfText.contains("?"))
                    {
                        if(lineOfText.contains(CORRECT_MARKER))
                        {
                            String[] solution = new String[2];
                            // split string to exclude "*"
                            solution = lineOfText.split(MARKER_PREFIX + CORRECT_MARKER);
                            // insert question without "*" into both arrays
                            questions[category][answerCount][questionCount] = solution[0];
                            correctResponse[category][answerCount] = solution[0];
                            questionCount = questionCount + 1;
                        }
                        else if(lineOfText.contains("?"))
                        {
                            // insert question into array
                            questions[category][answerCount][questionCount] = lineOfText;
                            questionCount = questionCount + 1;
                        }
                    }
                }
                else
                {
                    isExit = true;
                }
            }
            while(!isExit);
        }
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    private void printData()
    {
        // put your code here
        categories = new String[CATEGORY_COUNT];
        categoryFileNames = new String[CATEGORY_COUNT];
        int counter = 0;
        BufferedReader inputFile = null;
        String lineOfText = "";
        String fileName = "";
        boolean isExit = false;
        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(categoryFileName));
                System.out.println("File: " + categoryFileName);
            }
            catch(IOException exception)
            {
                System.out.println(exception);
            }
        }
        while(inputFile == null);
        System.out.println("Here's the categories below");
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
                // print line read into console
                System.out.println(lineOfText);
                // insert category into array
                categories[counter] = lineOfText;
                // convert category to filename
                fileName = categories[counter].toLowerCase()+".txt";
                // insert file name into array
                categoryFileNames[counter] = fileLocationPrefix + fileName;
                counter = counter + 1;
            }
            else
            {
                isExit = true;
            }
        }
        while(!isExit);
        numberOfCategories = counter;
        System.out.println("----------------------");

        // get answers and questions

        answers = new String[CATEGORY_COUNT][ANSWER_COUNT];
        questions = new String[CATEGORY_COUNT][ANSWER_COUNT][QUESTION_COUNT];
        correctResponse = new String[CATEGORY_COUNT][ANSWER_COUNT];
        for(int category = 0; category<CATEGORY_COUNT; category++)
        {
            // reset answer and question count
            int answerCount = 0;
            int questionCount = 0;
            // reset exit condition
            isExit = false;
            do
            {
                try
                {
                    inputFile = new BufferedReader (new FileReader(categoryFileNames[category]));
                    System.out.println("Reading file: " + categoryFileNames[category]);
                }
                catch(IOException exception)
                {
                    System.out.println(exception);
                }
            }
            while(inputFile == null);
            System.out.println("Here's the answers and questions below");
            System.out.println("-------------------------");
            do
            {
                try
                {
                    lineOfText = inputFile.readLine();
                } // end of try
                catch(IOException exception)
                {
                    System.out.println(exception);
                } // end of catch(IOException exception)
                if(lineOfText!=null)
                {
                    // print line read into console
                    System.out.println(lineOfText);
                    // determine whether line of text is question or answer
                    if(lineOfText.contains("."))
                    {
                        if(answerCount == 0 && questionCount == 0)
                        {
                            // do not increment answer count if it is the first time reading an answer
                        }
                        else
                        {
                            answerCount = answerCount + 1;
                        }
                        // insert answer into array
                        answers[category][answerCount] = lineOfText;
                        // reset question count to 0
                        questionCount = 0;
                    }
                    else if(lineOfText.contains("?"))
                    {
                        if(lineOfText.contains(CORRECT_MARKER))
                        {
                            String[] solution = new String[2];
                            // split string to exclude "*"
                            solution = lineOfText.split(MARKER_PREFIX + CORRECT_MARKER);
                            // insert question without "*" into both arrays
                            questions[category][answerCount][questionCount] = solution[0];
                            System.out.println("Answer: " + solution[0]);
                            correctResponse[category][answerCount] = solution[0];
                            System.out.println("Answer recorded.");
                            questionCount = questionCount + 1;
                        }
                        else if(lineOfText.contains("?"))
                        {
                            // insert question into array
                            questions[category][answerCount][questionCount] = lineOfText;
                            questionCount = questionCount + 1;
                        }
                    }
                }
                else
                {
                    isExit = true;
                }
            }
            while(!isExit);
        }
    }

    public String getInfo(int categoryNumber, int answerNumber)
    {
        String info = "";
        String category = "Category: " + categories[categoryNumber] + ";";
        String answer = " Answer: " + answers[categoryNumber][answerNumber] + ";";
        String question = " Questions: ";
        for (int index = 0; index < QUESTION_COUNT; index++)
        {
            if(index == 4)
            {
                question = question + questions[categoryNumber][answerNumber][index];
            }
            else
            {
                question = question + questions[categoryNumber][answerNumber][index] + ",";
            }
        }
        info = category + answer + questions;
        return info;
    }
}