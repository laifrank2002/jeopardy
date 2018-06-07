
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // constants
    private static final String DEFAULT_NAME = "Player";

    // instance variables - replace the example below with your own
    private static int score;
    private static String name;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        score = 0;
        name = DEFAULT_NAME;
    }

    public Player(String name)
    {
        score = 0;
        if(name != null)
        {
            this.name = name;
        }
    }


    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int returnScore()
    {
        // put your code here
        return this.score;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public String returnName()
    {
        // put your code here
        return this.name;
    }
}
