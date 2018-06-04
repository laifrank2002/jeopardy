import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
/**
 * Write a description of class FileIO here.
 * 
 * @author William Wang
 * @version 1.0 2018.05.29
 * @version 1.1 2018.05.30
 * @version 1.2 2018.05.31
 * @version 2.0 2018.06.01
 * @version 2.1 2018.06.02
 * @version 2.2 2018.06.03
 */
public class FileIO
{
    // class constants
    private static final int CATEGORY_COUNT = 6;
    private static final int QUESTION_COUNT = 5;
    private static final int ANSWER_COUNT = 6;
    private static final String categoryFileName = "Data/categories.data";
    private static final String fileLocationPrefix = "Data/";
    private static final String MARKER_PREFIX = "\\";
    private static final String CORRECT_MARKER = "*";
    private static final String QUESTION_MARKER = "?";
    private static final String ANSWER_MARKER = ".";
    private static final String DATATYPE = ".data";
    
    // instance fields
    private static String[][] answers;
    private static String[] categories;
    private static String[] categoryFileNames;
    private static String[][] correctResponse;
    private static String[][][] questions;

    /* constructors */

    /**
     * Creates a jeopardy game.
     */
    public FileIO()
    {
        // initialise console buffered reader
        BufferedReader console = new BufferedReader (new InputStreamReader (System.in));
        // read data from files
        getData();
    } // end of constructor FileIO()

    /* accesors */

    /**
     * Returns this answer.
     * 
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @param answerNumber the answer number; must be non-negative; must be within the subscript of the array;
     * @return this answer.
     */
    public String getAnswer(int categoryNumber, int answerNumber)
    {
        return answers[categoryNumber][answerNumber];
    } // end of method getAnswer(int categoryNumber, int answerNumber)

    /**
     * Returns this category.
     * 
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @return this category.
     */
    public String getCategory(int categoryNumber)
    {
        return categories[categoryNumber];
    } // end of method getCategory(int categoryNumber)

    /**
     * Returns a string representation of this component.
     * 
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @param answerNumber the category number; must be non-negative; must be within the subscript of the array;
     * @return a string representation of this component.
     */
    public String getInfo(int categoryNumber, int answerNumber)
    {
        String info = "";
        String category = "Category: " + categories[categoryNumber] + ".";
        String answer = " Answer: " + answers[categoryNumber][answerNumber] + "";
        String question = " Questions: ";
        for (int index = 0; index < QUESTION_COUNT; index++)
        {
            if(index == QUESTION_COUNT-1)
            {
                question = question + questions[categoryNumber][answerNumber][index];
            }
            else
            {
                question = question + questions[categoryNumber][answerNumber][index] + ", ";
            }
        }
        info = category + answer + question;
        System.out.println(info);
        return info;
    } // end of method getInfo(int categoryNumber, int answerNumber)

    /**
     * Returns this question.
     * 
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @param answerNumber the answer number; must be non-negative; must be within the subscript of the array;
     * @param questionNumber the question number;
     * @return this question.
     */
    public String getQuestion(int categoryNumber, int answerNumber, int questionNumber)
    {
        return questions[categoryNumber][answerNumber][questionNumber];
    } // end of method getQuestion(int categoryNumber, int answerNumber, int questionNumber)

    /**
     * Returns a string representation of this component.
     * 
     * @return a string representing this component
     */
    public String toString()
    {
        return
        getClass().getName()
        + "["
        + "Categories: "  
        + ", answers: "  
        + "]";
    } // end of method toString()

    /* private methods */

    /*
     * Imports and stores the data of this jeopardy game.
     */
    private void getData()
    {
        // initialize variables
        answers = new String[CATEGORY_COUNT][ANSWER_COUNT];
        categories = new String[CATEGORY_COUNT];
        categoryFileNames = new String[CATEGORY_COUNT];
        correctResponse = new String[CATEGORY_COUNT][ANSWER_COUNT];
        int counter = 0;
        String fileName = "";
        BufferedReader inputFile = null;
        boolean isExit = false;
        String lineOfText = "";
        questions = new String[CATEGORY_COUNT][ANSWER_COUNT][QUESTION_COUNT];

        // attempt to establish buffered reader with category file
        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(categoryFileName));
            } // end of try
            catch(IOException exception)
            {
                System.out.println(exception);
            } // end of catch(IOException exception)
        }
        while(inputFile == null);
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
            if(lineOfText != null)
            {
                // insert category into array
                categories[counter] = lineOfText;
                // convert category to filenamehttp://touque.ca/EC/programming/Java/assignments/ca/Jeopardy/images/5x5_answer.png
                fileName = categories[counter].toLowerCase()+ DATATYPE;
                // insert file name into array
                categoryFileNames[counter] = fileLocationPrefix + fileName;
                counter = counter + 1;
            } // end of if(lineOfText != null)
            else
            {
                isExit = true;
            } // end of else
        }
        while(!isExit);
        
        // get answers, questions and correct responses
        
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
                } // end of try
                catch(IOException exception)
                {
                    System.out.println(exception);
                } // end of catch(IOException exception)
            }
            while(inputFile == null);
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
                    if(lineOfText.contains(ANSWER_MARKER))
                    {
                        if(answerCount == 0 && questionCount == 0)
                        {
                            // do not increment answer count if it is the first time reading an answer
                        } // end of if(answerCount == 0 && questionCount == 0)
                        else
                        {
                            answerCount = answerCount + 1;
                        } // end of else
                        // insert answer into array
                        answers[category][answerCount] = lineOfText;
                        // reset question count to 0
                        questionCount = 0;
                    } // end of if(LineOfText.contains(ANSWER_MARKER))
                    else if(lineOfText.contains(QUESTION_MARKER))
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
                        } // end of if(lineOfText.contains(CORRECT_MARKER))
                        else
                        {
                            // insert question into array
                            questions[category][answerCount][questionCount] = lineOfText;
                            questionCount = questionCount + 1;
                        } // end of else
                    } // end of else if(lineOfText.contains(QUESTION_MARKER))
                } // end of if(lineOfText!=null)
                else
                {
                    isExit = true;
                } // end of else
            }
            while(!isExit);
        } // end of for(int category = 0; category<CATEGORY_COUNT; category++)
    } // end of method getData
} // end of class FileIO