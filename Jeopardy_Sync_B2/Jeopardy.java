
/**
 * Central Logic Unit for Jeopardy. Handles data processing.
 * 
 * @author Frank Lai 
 * @version 2018-05-31 1.00
 */
public class Jeopardy
{
    // Constants.
    public static final int ANSWER_COUNT = 5;
    public static final int QUESTION_COUNT = 5;
    public static final int CATEGORY_SIZE = 6;
    public static final int MONEY_INTERVAL = 200;
    public static final String MONEY_PREFIX = "$";
    
    // Instance.
    private static OutputWindow window;
    
    public static int answer_count;
    public static int question_count;
    public static int category_size;
    
    // Data.
    private static FileIO fileData;
    private static Question[][] questions_data;
    private static String dataFile = "data/categories.txt";
    /**
     * Main method.
     * 
     * @param argument not used
     */
    public static void main(String[] argument)
    {
        // Init.
        fileData = new FileIO();
        // Useless window that looks nice.
        InitSplashScreen splashScreen = new InitSplashScreen();
        // Load data.
        loadData();
        // Create rendering window.
        window = new OutputWindow();
        // Delete splash window now that everything is loaded.
        splashScreen.destroySplashScreen();
    } // end of method main(String[] argument)
    
    
    /**
     * Inquires for an answer for a question, using a dialog popup.
     * 
     * @param category the category of the question.
     * @param question the question number.
     */
    public static void askQuestion(int category, int question)
    {
        
    } // end of method askQuestion(int category, int question)
    
    /**
     * Does preprocessing, ie loads data.
     */
    private static void loadData()
    {
        
        // Temp get testing data.
        
        answer_count = ANSWER_COUNT;
        question_count = QUESTION_COUNT;
        category_size = CATEGORY_SIZE;
        
        //questions_data = new Question[category_size][question_count];
        //questions_data = Reader.loadData;
        questions_data = new Question[][]
            {
                {new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)}
                , {new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)}
                , {new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)}
                , {new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)}
                , {new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)}
                , {new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)
                , new Question("Hello",new String[]{"What is a greeting?"},0)}
            };
    } // end of method loadData()
    
} // end of class Jeopardy
