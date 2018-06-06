import java.awt.Font;
import java.io.InputStream;
import java.io.File;
/**
 * A font object that loads and keeps a font for later use.
 * 
 * @author Frank Lai
 * @version 2018-06-01 1.00
 */
public class FontLoader
{

    /**
     * Loads fonts.
     * 
     * @param name the name of the file of the font
     */
    public static Font getFont(String name) 
    {
        Font font = null;
        // Load font.
        try
        {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(name));
            font = font.deriveFont(24.0f);
        }
        catch (Exception exception)
        {
            // Default Font.
            font = new Font("serif",Font.PLAIN,24);
        } // end of catch (Exception exception)
        return font;
    } // end of getFont(String name)
    
    /**
     * Loads fonts with a specific size.
     * 
     * @param name the name of the file of the font
     * @param size the size of the font
     */
    public static Font getFont(String name, int size) 
    {
        Font font = null;
        // Load font.
        try
        {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(name));
            font = font.deriveFont((float)size);
        }
        catch (Exception exception)
        {
            // Default Font.
            font = new Font("serif",Font.PLAIN,size);
        } // end of catch (Exception exception)
        return font;
    } // end of getFont(String name, int size)
} // end of class FontLoader
