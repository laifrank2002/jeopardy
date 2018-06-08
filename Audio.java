import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.embed.swing.JFXPanel;
/**
 * Write a description of class Audio here.
 * 
 * @author William Wang 
 * @version 1.0 2018.06.08
 */
public class Audio
{
    // constants
    private static final String THEME_LOCATION = "Music/theme.mp3";
    
    // instance fields
    private JFXPanel fxPanel = null;
    private MediaPlayer mediaPlayer = null;
    private Media song = null;
    
    public Audio()
    {
        fxPanel = new JFXPanel();
        song = new Media(new File(THEME_LOCATION).toURI().toString());
        mediaPlayer = new MediaPlayer(song);
    }
    
    public void playTheme()
    {
        mediaPlayer.play();
    }
    
    public void stopTheme()
    {
        mediaPlayer.stop();
    }
    
    public void pauseTheme()
    {
        mediaPlayer.pause();
    }
}
