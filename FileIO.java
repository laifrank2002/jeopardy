import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
/**
 * Reads and stores game data.
 *
 * @author William Wang
 * @version 1.0 2018.05.29
 * @version 1.1 2018.05.30
 * @version 1.2 2018.05.31
 * @version 2.0 2018.06.01
 * @version 2.1 2018.06.02
 * @version 2.2 2018.06.03
 * @version 3.0 2018.06.12
 */
public class FileIO
{
    // class constants
    private static final int CATEGORY_COUNT = 6;
    private static final int QUESTION_COUNT = 5;
    private static final int ANSWER_COUNT = 6;
    private static final String DEFAULT_FILE = "Data/categories.data";
    private static final String FILE_LOCATION_PREFIX = "Data/";
    private static final String MARKER_PREFIX = "\\";
    private static final String CORRECT_MARKER = "*";
    private static final String QUESTION_MARKER = "?";
    private static final String ANSWER_MARKER = ".";
    private static final String DATATYPE = ".data";

    // instance fields
    // arrays
    private static String[][] answers;
    private static String[] categories;
    private static String[] categoryFileNames;
    private static String[][] correctResponse;
    private static String[][][] questions;

    private static File categoryFile;
    private static int category_count;
    private static int question_count;
    private static int answer_count;
    /* constructors */

    /**
     * Reads and stores data from the default game files.
     */
    public FileIO()
    {
        // initialise console buffered reader
        BufferedReader console = new BufferedReader (new InputStreamReader (System.in));
        // read data from files
        getData(DEFAULT_FILE);
    } // end of constructor FileIO()

    public FileIO(String categoryFileLocation)
    {
        getData(categoryFileLocation);
    }

    /* accesors */
    /**
     * Returns the category count, question count, and answer count in one array.
     *
     * @return the category, question, and answer count.
     */
    public static int[] getParameters()
    {
        return new int[]{category_count,answer_count,question_count};
    } // end of method getParameters()

    /**
     * Returns the category count.
     */
    public static int getCategoryNumber()
    {
        return category_count;
    } // end of getCategoryNumber()

    /**
     * Returns this answer.
     *
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @param answerNumber the answer number; must be non-negative; must be within the subscript of the array;
     * @return this answer.
     */
    public static String getAnswer(int categoryNumber, int answerNumber)
    {
        return answers[categoryNumber][answerNumber];
    } // end of method getAnswer(int categoryNumber, int answerNumber)

    /**
     * Returns this category.
     *
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @return this category.
     */
    public static String getCategory(int categoryNumber)
    {
        return categories[categoryNumber];
    } // end of method getCategory(int categoryNumber)

    /**
     * Returns the correct answer.
     *
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @param answerNumber the answer number; must be non-negative; must be within the subscript of the array;
     * @return the correct answer.
     */
    public static String getCorrectAnswer(int categoryNumber, int answerNumber)
    {
        return correctResponse[categoryNumber][answerNumber];
    } // end of method getCorrectAnswer(int categoryNumber, int answerNumber)

    /**
     * Imports and stores the data of this game.
     *
     * @param categoryNumber the category number; must be non-negative;
     * @param answerNumber the answer number; must be non-negative;
     * @param questionNumber the question number; must be non-negative;
     * @param categoryFileLocation the location of the file;
     */
    public static void getData(String categoryFileLocation)
    {
        int counter = 0;
        BufferedReader inputFile = null;
        boolean isExit = false;
        String lineOfText = "";
        int questionNumber = 0;
        String filePrefix = "";

        // Autodetect category number. MUST TO DO
        int categoryNumber = 0;
        int answerNumber = 0;
        // attempt to establish buffered reader with category file
        if(categoryFileLocation == null)
        {
            categoryFileLocation = DEFAULT_FILE;
        }
        else
        {
            // get path of folder of game data.
            File file = new File(categoryFileLocation);
            filePrefix = file.getParent();
        }
        // detect number of categories.
        categoryNumber = detectNumberOfCategories(categoryFileLocation);
        counter = 0;
        if(categoryNumber <= 0)
        {
            categoryNumber = CATEGORY_COUNT;
        } // end of if(categoryNumber<=0)
        // initialise question number.
        questionNumber = QUESTION_COUNT;
        // initialise arrays with the detected category number.
        categories = new String[categoryNumber];
        categoryFileNames = new String[categoryNumber];
        // reset conditions.
        isExit = false;
        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(categoryFileLocation));
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
                String fileName = categories[counter].toLowerCase()+ DATATYPE;
                // insert file name into array
                categoryFileNames[counter] = filePrefix + MARKER_PREFIX + fileName;
                // increment counter.
                counter = counter + 1;
            } // end of if(lineOfText != null)
            else
            {
                isExit = true;
            } // end of else
            if(isExit)
            {
                try
                {
                    inputFile.close();
                } // end of try
                catch(IOException exception)
                {
                    System.out.println(exception);
                } // end of catch(IOException exception)
            } // end of if(isExit)
        }
        while(!isExit);
        // autodetects answer number.
        answerNumber = detectNumberOfAnswer(categoryFileNames[0]);
        if(answerNumber <= 0)
        {
            answerNumber = ANSWER_COUNT;
        } // end of if(answerNumber<=0)
        // initialises arrays with detected category and answer count.
        answers = new String[categoryNumber][answerNumber];
        correctResponse = new String[categoryNumber][answerNumber];
        questions = new String[categoryNumber][answerNumber][questionNumber];
        for(int category = 0; category<categoryNumber; category++)
        {
            // resets answer and question count
            int answerCount = 0;
            int questionCount = 0;
            // reset exit condition
            isExit = false;
            boolean isFirstTime = true;
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
                    if (isFirstTime)
                    {
                        // stores the answer in the array
                        answers[category][answerCount] = lineOfText;
                        // do not increment answer count if it is the first time reading an answer
                        isFirstTime = false;
                    } // end of if(answerCount == 0 && questionCount == 0)
                    else if(questionCount == QUESTION_COUNT)
                    {
                        answerCount = answerCount + 1;
                        // insert answer into array
                        answers[category][answerCount] = lineOfText;
                        // reset question count to 0
                        questionCount = 0;
                        // increment answer count.

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
        } // end of for(int category = 0; category<categoryNumber; category++)
    } // end of getData()

    /**
     * Returns a string representation of this component.
     *
     * @param categoryNumber the category number; must be non-negative; must be within the subscript of the array;
     * @param answerNumber the category number; must be non-negative; must be within the subscript of the array;
     * @return a string representation of this component.
     */
    public static String getInfo(int categoryNumber, int answerNumber)
    {
        String info = "";
        String category = "Category: " + categories[categoryNumber] + ".";
        String answer = " Answer: " + answers[categoryNumber][answerNumber] + "";
        String question = " Questions: ";
        for (int index = 0; index < QUESTION_COUNT; index++)
        {
            if(index == QUESTION_COUNT-1)
            {
                // add final question to the question string without comma.
                question = question + questions[categoryNumber][answerNumber][index];
            }
            else
            {
                // add next question to the question string with punctuation.
                question = question + questions[categoryNumber][answerNumber][index] + ", ";
            }
        }
        // info gets the strings category, answer and question all together.
        info = category + answer + question;
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
    public static String getQuestion(int categoryNumber, int answerNumber, int questionNumber)
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
        + ", questions: "
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
        getCategories(DEFAULT_FILE);

        // get answers, questions and correct responses
        getQuestions();
    } // end of method getData

    private static int detectNumberOfCategories(String categoryFileLocation)
    {
        int counter = 0;
        BufferedReader inputFile = null;
        boolean isExit = false;
        String lineOfText = "";
        // establishes connection to the specified file.
        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(categoryFileLocation));
            } // end of try
            catch(IOException exception)
            {

            } // end of catch(IOException exception)
        }
        while(inputFile == null);
        do
        {
            try
            {
                // read the next line of text
                lineOfText = inputFile.readLine();
            } // end of try
            catch(IOException exception)
            {
                System.out.println(exception);
            } // end of catch(IOException exception)
            if(lineOfText != null)
            {
                // increment category counter if the line is not null
                counter = counter + 1;
            } // end of if(lineOfText != null)
            else
            {
                isExit = true;
            } // end of else
            if (isExit)
            {
                try
                {
                    // terminates connection once there are no more lines to read.
                    inputFile.close();
                } // end of try
                catch(IOException exception)
                {

                } // end of catch(IOException exception)
            } // end of if(isExit)
        }
        while(!isExit);
        // return the number of categories counted
        return counter;
    } // end of method detectNumberOfCategories(String categoryFileLocation)

    public static int detectNumberOfAnswer(String categoryFileLocation)
    {
        int counter = 0;
        BufferedReader inputFile = null;
        boolean isExit = false;
        String lineOfText = "";
        // establishes connection to the specified file.
        do
        {
            try
            {
                inputFile = new BufferedReader (new FileReader(categoryFileLocation));
            } // end of try
            catch(IOException exception)
            {

            } // end of catch(IOException exception)
        }
        while(inputFile == null);
        do
        {
            try
            {
                // read the next line of text
                lineOfText = inputFile.readLine();
            } // end of try
            catch(IOException exception)
            {
                System.out.println(exception);
            } // end of catch(IOException exception)
            if(lineOfText != null)
            {
                // determines whether the line of text is an answers
                if(lineOfText.contains(ANSWER_MARKER)&&!lineOfText.contains(QUESTION_MARKER))
                {
                    // increments answer counter if the line is answer.
                    counter = counter + 1;
                }
            } // end of if(lineOfText != null)
            else
            {
                isExit = true;
            } // end of else
            if (isExit)
            {
                try
                {
                    // terminates connection once there are no more lines to read.
                    inputFile.close();
                } // end of try
                catch(IOException exception)
                {

                } // end of catch(IOException exception)
            } // end of if(isExit)
        }
        while(!isExit);
        // return the number of answers counted
        return counter;
    } // end of method detectNumberOfAnswer(String categoryFileLocation)

    private static void getCategories(String categoryFileName)
    {
        int counter = 0;
        BufferedReader inputFile = null;
        boolean isExit = false;
        String lineOfText = "";
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
                // convert category to filename http://touque.ca/EC/programming/Java/assignments/ca/Jeopardy/images/5x5_answer.png
                String fileName = categories[counter].toLowerCase()+ DATATYPE;
                // insert file name into array
                categoryFileNames[counter] = FILE_LOCATION_PREFIX + fileName;
                counter = counter + 1;
            } // end of if(lineOfText != null)
            else
            {
                isExit = true;
            } // end of else
        }
        while(!isExit);
    }

    private static void getQuestions()
    {
        int counter = 0;
        BufferedReader inputFile = null;
        boolean isExit = false;
        String lineOfText = "";
        for(int category = 0; category < CATEGORY_COUNT; category++)
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
                    if(questionCount == 5)
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
        }
    }
} // end of class FileIO
