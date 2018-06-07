import java.io.File;
/**
 * Central Logic Unit for Jeopardy. Handles data processing.
 * 
 * @author Frank Lai 
 * @version 2018-05-31 1.00
 */
public class Jeopardy
{
    // Constants.
    public static final int PLAYER_COUNT = 4;
    public static final int ANSWER_COUNT = 5;
    public static final int QUESTION_COUNT = 5;
    public static final int CATEGORY_SIZE = 6;
    public static final int MONEY_INTERVAL = 200;
    public static final String MONEY_PREFIX = "$";
    
    // Shared fields used by data renderer.
    private static OutputWindow window;
    
    public static int answer_count;
    public static int question_count;
    public static int category_count;
    
    public static int currentPlayer;
    public static int player_count;
    // Data.
    private static FileIO fileData;
    private static Question[][] questions_data;
    private static Player[] player;
    /**
     * Main method.
     * 
     * @param argument not used
     */
    public static void main(String[] argument)
    {
        // Init load data.
        fileData = new FileIO();
        player_count = PLAYER_COUNT;
        currentPlayer = 0;
        player = new Player[player_count];
        for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
        {
            // Enter name of player dialog option later
            player[playerIndex] = new Player();
        } // end of for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
        // Useless window that looks nice.
        InitSplashScreen splashScreen = new InitSplashScreen();
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
     * Loads and inits a new Jeopardy based on a new file.
     * 
     * @param file the File that contains the Jeopardy Data.
     */
    public static void loadNewJeopardy(File category)
    {
        FileIO.getData(category);
        int[] data = FileIO.getParameters();
        int answer_count = data[1];
        int question_count = data[2];
        int category_count = data[0];
        window.reloadJeopardy(question_count,answer_count,category_count);
    } // end of method loadNewJeopardy(File category)
    
    /**
     * Changes the current player.
     */
    public static void changePlayer()
    {
        currentPlayer++;
        if(currentPlayer == player_count)
        {
            currentPlayer = 0;
        } // end of if(currentPlayer == player_count)
    } // end of method changePlayer()
    
    /**
     * Returns the score of a player
     * 
     * @param index the index of the player
     * @return the score the scores of the player
     */
    public static int getScore(int playerIndex)
    {
        // Check if inbounds.
        if(playerIndex >= 0 && playerIndex < player_count)
        {
            return player[playerIndex].returnScore();
        } // end of (int score, int playerIndex)
        return 0;
    } // end of method getScores()
    
    /**
     * Adds scores.
     * 
     * @param score the score to be added
     * @param player the lucky player
     */
    public static void addScore(int score, int playerIndex)
    {
        // Check if inbounds.
        if(playerIndex >= 0 && playerIndex < player_count)
        {
            player[playerIndex].addScore(score);
        } // end of (int score, int playerIndex)
    } // end of method addScore(int score, int playerIndex)
    /**
     * Subtracts scores.
     * 
     * @param score the score to be subtracted
     * @param player the unlucky player
     */
    public static void subtractScore(int score, int playerIndex)
    {
        // Check if inbounds.
        if(playerIndex >= 0 && playerIndex < player_count)
        {
            player[playerIndex].subtractScore(score);
        } // end of (int score, int playerIndex)
    } // end of method subtractScore(int score, int playerIndex)
    
    /**
     * Gets the name of the player.
     * 
     * @param index the index of the player
     * @return the name of the player
     */
    public static String getPlayerName(int playerIndex)
    {
        // Check if inbounds.
        if(playerIndex > 0 && playerIndex < player_count)
        {
            player[playerIndex].returnName();
        } // end of (int score, int playerIndex)
        return "Player";
    } // end of getPlayerName(int playerIndex)
} // end of class Jeopardy
