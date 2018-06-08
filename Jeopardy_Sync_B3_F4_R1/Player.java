
/**
 * A data representation of a player.
 * 
 * @author William Wang
 * @version 2018-06-06
 */
public class Player
{
    // constants
    private static final String DEFAULT_NAME = "Player";

    // instance variables - replace the example below with your own
    private int score;
    private String name;

    /**
     * Creates a default player.
     */
    public Player()
    {
        // initialise instance variables
        score = 0;
        name = DEFAULT_NAME;
    } // end of Player()
    /**
     * Creates a player with a name.
     * 
     * @param name the name of the player
     */
    public Player(String name)
    {
        score = 0;
        if(name != null)
        {
            this.name = name;
        } // end of if(name != null)
        name = "";
    } // end of Player(String name)

    /**
     * Gets the score of the player.
     * 
     * @return the score of the player
     */
    public int returnScore()
    {
        // put your code here
        return this.score;
    }
    
    /**
     * Gets the name of the player.
     * 
     * @return the name of the player
     */
    public String returnName()
    {
        // put your code here
        return name;
    } // end of method returnName()
    
    /**
     * Adds a score to the player.
     * 
     * @param score the amount to add to
     */
    public void addScore(int score)
    {
        if (score > 0)
        {
            this.score = this.score + score;
        } // end of if (score > 0)
    } // end of addScore(int score)
    /**
     * Subtracts a score from the player.
     * 
     * @param score the amount of take from
     */
    public void subtractScore(int score)
    {
        if (score > 0)
        {
            this.score = this.score - score;
        } // end of if (score > 0)
    } // end of subtractScore(int score)
    
} // end of class Player
