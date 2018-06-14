import java.io.File;
import javax.swing.JOptionPane;
/**
 * Central Logic Unit for Jeopardy. Handles data processing related to players.
 * 
 * @author Frank Lai 
 * @version 2018-05-31 1.00
 */
public class Jeopardy
{
    // Constants.
    public static final int PLAYER_COUNT = 4;
    public static final int MONEY_INTERVAL = 200;
    public static final String MONEY_PREFIX = "$";

    // Shared fields used by data renderer.
    public static OutputWindow window;

    public static int answer_count;
    public static int question_count;
    public static int category_count;

    public static int currentPlayer;
    public static int player_count;
    // Data.
    private static FileIO fileData;
    private static Question[][] questions_data;
    private static Player[] player;
    private static HighScore score;
    private static Audio audio;
    /**
     * Main method.
     * 
     * @param argument not used
     */
    public static void main(String[] argument)
    {
        player_count = PLAYER_COUNT;
        // Initializes load data.
        fileData = new FileIO();
        score = new HighScore();
        audio = new Audio();
        createPlayerData();
        // Useless window that looks nice.
        InitSplashScreen splashScreen = new InitSplashScreen();
        // Create rendering window.
        window = new OutputWindow();
        // Delete splash window now that everything is loaded.
        splashScreen.destroySplashScreen();

        // Reload after because the elements needed to have already been created.
        reloadPlayerData();
        window.updateScore();
    } // end of method main(String[] argument)
    
    /**
     * Gets fileio.
     * 
     * @return the fileio.
     */
    public static FileIO getFileIO()
    {
        // Ensure no nulls.
        if (fileData != null)
        {
            return fileData;
        }
        else
        {
            return fileData = new FileIO();
        }
    } // end of getFileIO()
    
    /**
     * Sets the player count.
     * 
     * @param players the number of players.
     */
    public static void setPlayerCount(int players)
    {
        if(players > 0)
        {
            player_count = players;
        } // end of if(player > 0)
    } // end of method setPlayerCount()
    /**
     * Resets all jeopardy data.
     */
    public static void reloadPlayerData()
    {
        currentPlayer = 0;
        for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
        {
            // Enter name of player dialog option.
            String playerName = JOptionPane.showInputDialog(window.getFrame(), "Enter your name player " + (playerIndex+1) + "!");
            player[playerIndex].setName(playerName);
            player[playerIndex].subtractScore(player[playerIndex].returnScore());
        } // end of for(int playerIndex = 0; playerIndex < player_count; playerIndex++)

    } // end of method resetPlayerData()

    /**
     * Resets all jeopardy data with specified parameters.
     * 
     * @param players the number of players
     */
    public static void reloadPlayerData(int players)
    {
        player = new Player[players];
        player_count = players;
        currentPlayer = 0;
        for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
        {
            player[playerIndex] = new Player();
            // Enter name of player dialog option.
            String playerName = JOptionPane.showInputDialog(window.getFrame(), "Enter your name player " + (playerIndex+1) + "!");
            player[playerIndex].setName(playerName);
            player[playerIndex].subtractScore(player[playerIndex].returnScore());
        } // end of for(int playerIndex = 0; playerIndex < player_count; playerIndex++)

    } // end of method resetPlayerData()

    /**
     * Saves high score.
     */
    public static void saveHighScores()
    {
        for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
        {
            // Enter name of player dialog option later
            score.recordHighScore(player[playerIndex].returnName(), player[playerIndex].returnScore());
        } // end of for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
    } // end of method saveHighScores()

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
        if(playerIndex >= 0 && playerIndex < player_count)
        {
            return player[playerIndex].returnName();
        } // end of (int score, int playerIndex)
        return "NameNotAvailible";
    } // end of getPlayerName(int playerIndex)

    /**
     * Plays the theme.
     */
    public static void playTheme()
    {
        audio.playTheme();
    } // end of playAudio();

    /**
     * Stops the theme.
     */
    public static void stopTheme()
    {
        audio.stopTheme();
    } // end of stopAudio();

    /**
     * Sets the audio volume.
     * 
     * @param a number from 1 to 100 which sets the volume.
     */
    public static void setVolume(int volume)
    {
        audio.setVolume(volume);
    } // end of setVolume()

    /*
     * Create player data.
     */
    private static void createPlayerData()
    {
        player_count = PLAYER_COUNT;
        currentPlayer = 0;
        player = new Player[player_count];
        for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
        {
            // Enter name of player dialog option.
            player[playerIndex] = new Player();
        } // end of for(int playerIndex = 0; playerIndex < player_count; playerIndex++)
    } // end of createPlayerData()

} // end of class Jeopardy
