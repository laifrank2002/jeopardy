import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.embed.swing.JFXPanel;
/**
 * Plays the jeopardy song.
 * 
 * @author William Wang 
 * @version 1.0 2018.06.08
 */
public class Audio
{
    // constants
    private static final String THEME_LOCATION = "Music/theme.mp3";
    private static final int MAX_VOLUME = 100;
    // instance fields
    private JFXPanel fxPanel = null;
    private MediaPlayer mediaPlayer = null;
    private Media song = null;

    public Audio()
    {
        fxPanel = new JFXPanel();
        song = new Media(new File(THEME_LOCATION).toURI().toString());
        mediaPlayer = new MediaPlayer(song);
    } // end of constructor Audio()

    public void playTheme()
    {
        mediaPlayer.play();
    } // end of method playTheme();

    public void stopTheme()
    {
        mediaPlayer.stop();
    } // end of method stopTheme();

    public void pauseTheme()
    {
        mediaPlayer.pause();
    } // end of method pauseTheme();

    public void setVolume(int soundVolume)
    {
        // Code adapted from https://stackoverflow.com/questions/5215459/android-mediaplayer-setvolume-function
        // This is bette, prevents sudden audio conflicting with system32 api.
        if(soundVolume >= 0 && soundVolume <= MAX_VOLUME)
        {
            double audioLogarithm =  (double)(1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME)));
            mediaPlayer.setVolume(audioLogarithm);
        } // end of if(soundVolume && soundVolume <= MAX_VOLUME)
    } // end of method setVolume(int volume)
} // end of class Audio
