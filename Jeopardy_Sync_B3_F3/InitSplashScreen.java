import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A nice picture that lures players in before the game is loaded.
 *
 * @author Frank Lai
 * @version 2018-06-02
 */
public class InitSplashScreen
{
    // Window constants.
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 400;
    public static final String WINDOW_TITLE = "";
    public static final String IMAGE_PATH = "Images\\splash.jpg";

    public JFrame frame;
    public BufferedImage splashImage;
    /**
     * Creates a new Splash screen.
     */
    public InitSplashScreen()
    {
        frame = new JFrame();
        // Set size.
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        // Load Image.
        try
        {
            splashImage = ImageIO.read(new File(IMAGE_PATH));
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
            // No image, no point in having splash screen.
            return;
        }
        // Image Component.
        ImagePanel splash = new ImagePanel(splashImage);
        frame.add(splash);
        
        // Final Assurance.
        frame.setUndecorated(true);
        frame.validate();
        frame.pack();
        // Center.
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width/2-frame.getSize().width/2, 
        dimension.height/2-frame.getSize().height/2);
        // Set visible.
        frame.setVisible(true);
    } // end of constructor InitSplashScreen

    /**
     * Destroys the Splash screen.
     */
    public void destroySplashScreen()
    {
        frame.dispose();
    } // end of method DestroySplashScreen()

    /* Displays the image */
    private class ImagePanel extends JComponent {
        private BufferedImage componentImage;
        public ImagePanel(BufferedImage image) {
            componentImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(componentImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT,
            0, 0, componentImage.getWidth(), componentImage.getHeight(), this);
        }
    }

} // end of class InitSplashScreen