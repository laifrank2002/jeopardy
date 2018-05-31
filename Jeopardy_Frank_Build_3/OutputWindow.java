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
 * @author frank lai
 * @version 2018-05-30 1.03
 */
public class OutputWindow
{
    // Jeopardy constants.
    public static final int ANSWER_COUNT = 5;
    public static final int QUESTION_COUNT = 5;
    public static final int CATEGORY_SIZE = 5;
    // Window constants.
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 768;
    public static final String WINDOW_TITLE = "Jeopardy";
    // Control Panel constants.
    public static final int CONTROL_PANEL_WIDTH = 100;
    public static final int CONTROL_PANEL_HEIGHT = 768;
    // Jeopardy Panel constants.
    public static final int COLUMN_WIDTH = 150;
    public static final int CELL_WIDTH = 150;
    public static final int CELL_HEIGHT = 100;
    public static final Color CELL_TEXT_COLOR = Color.YELLOW;
    public static final Color CELL_COLOR = Color.BLUE;
    // GUI components.
    private JFrame frame;

    private JPanel controlPanel;
    private JButton controlPanel_quitButton;
    private JButton controlPanel_resetButton;
    private JButton controlPanel_loadButton;
    private JButton controlPanel_settingsButton;
    private JButton controlPanel_switchPlayerButton;

    private JPanel jeopardyPanel;
    private JPanel jeopardyPanel_categoryPanel;
    private JLabel[] jeopardyPanel_label;
    private JPanel jeopardyPanel_questionPanel;
    private JPanel [] jeopardyPanel_questionColumnPanel;
    private JButton[][] jeopardyPanel_button;

    // Instance fields.

    /**
     * Creates an output window that can be manipulated.
     */
    public OutputWindow()
    {
        createFrame();
        createJeopardyPanel();
    } // end of constructor OutputWindow()

    /**
     * Sets window to full size.
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
    
    /**
     * Destroys and rebuilds a jeopardy frame according to certain parameters.
     * 
     * @param answers the number of new answers
     * @param questions the number of new questions
     * @param categories the number of new categories
     * @param textfile the new textfile to load data from
     */
    public void reloadJeopardy(int answers, int questions, int categories, String dataFile)
    {
        // Remove from frame.
        frame.remove(jeopardyPanel);
        
        // Rebuild new frame.
        jeopardyPanel = createJeopardyPanel(questions,categories);

        // Readd to frame.
        frame.add(jeopardyPanel);
        frame.pack();
        frame.validate();
        frame.repaint();
        
    } // end of method reloadJeopardy(int answers, int questions, int categories, String dataFile)
    /*
     * Create the frame of the output window.
     */
    private void createFrame()
    {
        frame = new JFrame(WINDOW_TITLE);
        // Set size.
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));

        // Control Panel.
        controlPanel = createControlPanel();
        frame.add(controlPanel,BorderLayout.EAST);

        // Jeopardy Panel.
        jeopardyPanel = createJeopardyPanel();
        frame.add(jeopardyPanel,BorderLayout.CENTER);

        // Ensure visibility.
        frame.getContentPane().setBackground(Color.WHITE);
        frame.validate();
        frame.pack();
        frame.setVisible(true);
    } // end of method createFrame()

    /*
     * Create the jeopardy panel.
     */
    private JPanel createJeopardyPanel()
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();

        // Categories.
        jeopardyPanel_categoryPanel = new JPanel();
        jeopardyPanel_label = new JLabel[CATEGORY_SIZE];
        // Create all of the category headers.
        for (int category = 0; category < CATEGORY_SIZE; category++)
        {
            // Adds and centers label in its cell.
            jeopardyPanel_label[category] = new JLabel("I am a label!" + category, JLabel.CENTER);
            // Set size.
            jeopardyPanel_label[category].setPreferredSize(new Dimension(CELL_WIDTH,CELL_HEIGHT));
            // Change colour, and sets opaque in order to show it!
            jeopardyPanel_label[category].setBackground(CELL_COLOR);
            jeopardyPanel_label[category].setOpaque(true);
            // Change text colour.
            jeopardyPanel_label[category].setForeground(CELL_TEXT_COLOR);
            // Add.
            jeopardyPanel_categoryPanel.add(jeopardyPanel_label[category]);
        } // end of for (int category = 0; category < CATEGORY_SIZE; category++)

        // Questions.
        jeopardyPanel_questionPanel = new JPanel();
        jeopardyPanel_button = new JButton[CATEGORY_SIZE][QUESTION_COUNT];
        jeopardyPanel_questionColumnPanel = new JPanel[CATEGORY_SIZE];
        // Create all the questions.
        for (int category = 0; category < CATEGORY_SIZE; category++)
        {
            // Create new horizontal JPanel to manage layout.
            jeopardyPanel_questionColumnPanel[category] = new JPanel();
            // Set length size to very little in order to ensure horizontal layout.
            // Y value is set to window_height in order to accomodate as much height needed as possible. It hugs the bottom.  
            jeopardyPanel_questionColumnPanel[category].setPreferredSize(new Dimension(COLUMN_WIDTH, WINDOW_HEIGHT));
            // Create questions and add to JPanel.
            for (int question = 0; question < QUESTION_COUNT; question++)
            {
                // Make new JButton question and add to panel.
                jeopardyPanel_button[category][question] = new JButton("I am a button!" + "Coor(" + category + "," + question + ")");
                // Set size.
                jeopardyPanel_button[category][question].setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
                // Change colour.
                jeopardyPanel_button[category][question].setBackground(CELL_COLOR);
                // Change text colour.
                jeopardyPanel_button[category][question].setForeground(CELL_TEXT_COLOR);
                // Add to panel.
                jeopardyPanel_questionColumnPanel[category].add(jeopardyPanel_button[category][question]);
            } // end of for (int question = 0; question < QUESTION_COUNT; question++)

            // Add back to questionPanel.
            jeopardyPanel_questionPanel.add(jeopardyPanel_questionColumnPanel[category]);
        } // end of for (int category = 0; category < CATEGORY_COUNT; category++)

        // Build onto skeleton.
        // Note, redo layout with flow layout manager.
        skeletonPanel.add(jeopardyPanel_categoryPanel,BorderLayout.NORTH);
        skeletonPanel.add(jeopardyPanel_questionPanel,BorderLayout.SOUTH);

        return skeletonPanel;
    } // end of method createJeopardyPanel()
    
    /*
     * Create the jeopardy panel with set parameters.
     * 
     * @param answers the number of new answers
     * @param questions the number of new questions
     * @param categories the number of new categories
     */
    private JPanel createJeopardyPanel(int questions, int categories)
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();

        // Categories.
        jeopardyPanel_categoryPanel = new JPanel();
        jeopardyPanel_label = new JLabel[categories];
        // Create all of the category headers.
        for (int category = 0; category < categories; category++)
        {
            // Adds and centers label in its cell.
            jeopardyPanel_label[category] = new JLabel("I am a label!" + category, JLabel.CENTER);
            // Set size.
            jeopardyPanel_label[category].setPreferredSize(new Dimension(CELL_WIDTH,CELL_HEIGHT));
            // Change colour, and sets opaque in order to show it!
            jeopardyPanel_label[category].setBackground(CELL_COLOR);
            jeopardyPanel_label[category].setOpaque(true);
            // Change text colour.
            jeopardyPanel_label[category].setForeground(CELL_TEXT_COLOR);
            // Add.
            jeopardyPanel_categoryPanel.add(jeopardyPanel_label[category]);
        } // end of for (int category = 0; category < CATEGORY_SIZE; category++)

        // Questions.
        jeopardyPanel_questionPanel = new JPanel();
        jeopardyPanel_button = new JButton[categories][questions];
        jeopardyPanel_questionColumnPanel = new JPanel[categories];
        // Create all the questions.
        for (int category = 0; category < categories; category++)
        {
            // Create new horizontal JPanel to manage layout.
            jeopardyPanel_questionColumnPanel[category] = new JPanel();
            // Set length size to very little in order to ensure horizontal layout.
            // Y value is set to window_height in order to accomodate as much height needed as possible. It hugs the bottom.  
            jeopardyPanel_questionColumnPanel[category].setPreferredSize(new Dimension(COLUMN_WIDTH, WINDOW_HEIGHT));
            // Create questions and add to JPanel.
            for (int question = 0; question < questions; question++)
            {
                // Make new JButton question and add to panel.
                jeopardyPanel_button[category][question] = new JButton("I am a button!" + "Coor(" + category + "," + question + ")");
                // Set size.
                jeopardyPanel_button[category][question].setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
                // Change colour.
                jeopardyPanel_button[category][question].setBackground(CELL_COLOR);
                // Change text colour.
                jeopardyPanel_button[category][question].setForeground(CELL_TEXT_COLOR);
                // Add to panel.
                jeopardyPanel_questionColumnPanel[category].add(jeopardyPanel_button[category][question]);
            } // end of for (int question = 0; question < QUESTION_COUNT; question++)

            // Add back to questionPanel.
            jeopardyPanel_questionPanel.add(jeopardyPanel_questionColumnPanel[category]);
        } // end of for (int category = 0; category < CATEGORY_COUNT; category++)

        // Build onto skeleton.
        // Note, redo layout with flow layout manager.
        skeletonPanel.add(jeopardyPanel_categoryPanel,BorderLayout.NORTH);
        skeletonPanel.add(jeopardyPanel_questionPanel,BorderLayout.SOUTH);

        return skeletonPanel;
    } // end of method createJeopardyPanel()
    /*
     * Create the control panel.
     */
    private JPanel createControlPanel()
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();

        // Quit Button.
        controlPanel_quitButton = new JButton("Quit");
        skeletonPanel.add(controlPanel_quitButton);
        // Reset Button.
        controlPanel_resetButton = new JButton("Reset");
        skeletonPanel.add(controlPanel_resetButton);
        // Load Button.
        controlPanel_loadButton = new JButton("Load");
        skeletonPanel.add(controlPanel_loadButton);
        // Settings Button.
        controlPanel_settingsButton = new JButton("Settings");
        skeletonPanel.add(controlPanel_settingsButton);
        // Manually Switch Player Button.
        controlPanel_switchPlayerButton = new JButton("Switch Player");
        skeletonPanel.add(controlPanel_switchPlayerButton);
        
        // Set small size in order to have horizontal layout.
        skeletonPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH,CONTROL_PANEL_HEIGHT));
        
        return skeletonPanel;
    } // end of method createControlPanel()

    /*
     * Create the other decorations.
     */
    private void createDecorations()
    {

    } // end of method createDecorations()
    /*
     * Do the effects.
     */
    private void screenEffect()
    {

    } // end of method screenEffect()
}
