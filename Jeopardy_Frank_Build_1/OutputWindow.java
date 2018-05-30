import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates a GUI frame used for rendering
 * 
 * @frank lai
 * @2018-05-30
 */
public class OutputWindow
{
    // Window constants.
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 768;
    public static final String WINDOW_TITLE = "Jeopardy";
    // GUI components.
    private JFrame frame;
    // Instance fields.

    /**
     * Creates an output window that can be manipulated.
     */
    public OutputWindow()
    {
        createFrame();
    } // end of constructor OutputWindow()
    
    /**
     * Sets window to full screen.
     */
    public void fullscreenFrame()
    {
        // Get dimension.
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        // Set size.
        frame.setPreferredSize(new Dimension(width,height));
        // Set borders.
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    
    /**
     * Sets window to windowed size.
     */
    public void windowFrame()
    {
        // Set size.
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        // Set borders.
        frame.setExtendedState(JFrame.NORMAL); 
    }
    
    /*
     * Creates the frame of the output window.
     */
    private void createFrame()
    {
        frame = new JFrame(WINDOW_TITLE);
        // Set size.
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        // Ensure visibility.
        frame.getContentPane().setBackground(Color.WHITE);
        frame.validate();
        frame.pack();
        frame.setVisible(true);
    } // end of method createFrame()
    
    /*
     * Creates the jeopardy panel.
     */
    private void createJeopardyPanel()
    {
    
    }
    /*
     * Creates the control panel.
     */
    private void createControlPanel()
    {
        
    }
    /*
     * Creates the other decorations.
     */
    private void createDecorations()
    {
        
    } // end of method createDecorations()
    /*
     * Does the effects.
     */
    private void screenEffect()
    {
        
    } // end of method screenEffect()
}
