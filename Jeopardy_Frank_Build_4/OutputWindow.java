import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.ButtonUI;
/**
 * Creates a GUI frame used for rendering
 * 
 * @author frank lai
 * @version 2018-05-30 1.04
 */
public class OutputWindow
{
    // Jeopardy constants.
    public static final int ANSWER_COUNT = 4;
    public static final int QUESTION_COUNT = 5;
    public static final int CATEGORY_SIZE = 6;

    public static final int JEOPARDY_FONT_SIZE = 24;
    public static final int JEOPARDY_TITLE_FONT_SIZE = 108;

    public static final Font JEOPARDY_FONT = FontLoader.getFont("Fonts\\Jeopardy\\jeopardy.ttf",JEOPARDY_FONT_SIZE);
    public static final Font JEOPARDY_TITLE_FONT = FontLoader.getFont("Fonts\\Jeopardy\\jeopardy.ttf",JEOPARDY_TITLE_FONT_SIZE);
    // Window constants.
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 768;
    public static final String WINDOW_TITLE = "Jeopardy";
    // Answer Panel constants.
    public static final int ANSWER_PANEL_HEIGHT = 375;
    public static final int ANSWER_PANEL_WIDTH = 150 * CATEGORY_SIZE;
    public static final Color ANSWER_PANEL_COLOR = Color.BLUE;
    // Center Panel constants.
    public static final int CENTER_PANEL_HEIGHT = 700;
    public static final int CENTER_PANEL_WIDTH = 1000;
    // Control Panel constants.
    public static final int CONTROL_PANEL_BUTTON_WIDTH = 100;
    public static final int CONTROL_PANEL_BUTTON_HEIGHT = 75;
    public static final int CONTROL_PANEL_WIDTH = 100;
    public static final int CONTROL_PANEL_HEIGHT = 400;
    public static final Color CONTROL_PANEL_BUTTON_COLOR = Color.BLUE;
    public static final Color CONTROL_PANEL_BUTTON_TEXT_COLOR = Color.YELLOW;
    public static final Color CONTROL_PANEL_COLOR = Color.BLACK;
    // Jeopardy Panel constants.
    public static final int COLUMN_WIDTH = 155;
    public static final int COLUMN_HEIGHT = 375;
    public static final int COLUMN_PADDING = 5;
    public static final int CELL_WIDTH = 150;
    public static final int CELL_HEIGHT = 75;
    public static final Color CELL_TEXT_COLOR = Color.YELLOW;
    public static final Color CELL_COLOR = Color.BLUE;
    public static final Color FLASH_COLOR = Color.RED;
    public static final Color JEOPARDY_PANEL_COLOR = Color.BLACK;
    // Scoreboard Panel constants.

    // GUI components.
    private JFrame frame;

    private JPanel answerPanel;
    private JLabel answerPanel_questionLabel;
    private JButton[] answerPanel_answerButton;

    private JPanel centerPanel;

    private JPanel controlPanel;
    private JButton controlPanel_quitButton;
    private JButton controlPanel_resetButton;
    private JButton controlPanel_loadButton;
    private JButton controlPanel_helpButton;
    private JButton controlPanel_settingsButton;
    private JButton controlPanel_switchPlayerButton;

    private JPanel jeopardyPanel;
    private JPanel jeopardyPanel_categoryPanel;
    private JLabel[] jeopardyPanel_label;
    private JPanel jeopardyPanel_questionPanel;
    private JPanel [] jeopardyPanel_questionColumnPanel;
    private JButton[][] jeopardyPanel_button;

    private JPanel titleSplashPanel;
    private JLabel titleSplashPanel_label;
    // Instance fields.
    private int answer_count;
    private int category_size;
    private int question_count;

    private int player_count;
    private int[] playerScore;

    private boolean isJeopardyHidden;
    /**
     * Creates an output window that can be manipulated.
     */
    public OutputWindow()
    {
        answer_count = ANSWER_COUNT;
        question_count = QUESTION_COUNT;
        category_size = CATEGORY_SIZE;

        createFrame();
        isJeopardyHidden = false;
    } // end of constructor OutputWindow()

    /**
     * Creates an output window with custom jeopardy parameters.
     * 
     * @param answers the number of answers
     * @param questions the number of questions
     * @param categories the number of categories
     */
    public OutputWindow(int answers, int questions, int categories)
    {
        answer_count = answers;
        question_count = questions;
        category_size = categories;

        createFrame(questions,categories);
        isJeopardyHidden = false;
    } // end of constructor OutputWindow(int answers, int questions, int categories)

    /**
     * Creates an output window with custom jeopardy parameters and a set number of people.
     * 
     * @param answers the number of answers
     * @param questions the number of questions
     * @param categories the number of categories
     * @param players the number of players
     */
    public OutputWindow(int answers, int questions, int categories, int players)
    {
        answer_count = answers;
        question_count = questions;
        category_size = categories;
        player_count = players;

        createFrame(questions,categories);
        isJeopardyHidden = false;
        // Add scoreboard.

    } // end of constructor OutputWindow(int answers, int questions, int categories)
    /**
     * Sets window to full size.
     */
    public void fullscreenFrame()
    {
        // Get dimension.
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = graphicsDevice.getDisplayMode().getWidth();
        int height = graphicsDevice.getDisplayMode().getHeight();
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
     * Destroys and rebuilds a jeopardy panel according to certain parameters.
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
        isJeopardyHidden = false;
        answerPanel.setVisible(false);
        frame.add(jeopardyPanel);
        frame.pack();
        frame.validate();
        frame.repaint();

    } // end of method reloadJeopardy(int answers, int questions, int categories, String dataFile)

    /**
     * Switches between the main question panel and answer panel.
     */
    public void switchJeopardy()
    {
        if(isJeopardyHidden)
        {
            jeopardyPanel.setVisible(true);
            answerPanel.setVisible(false);
            isJeopardyHidden = false;
        }
        else
        {
            jeopardyPanel.setVisible(false);
            answerPanel.setVisible(true);
            isJeopardyHidden = true;
        } // end of if(isJeopardyHidden)
    } // end of method switchJeopardy()

    /*
     * Create the frame of the output window.
     */
    private void createFrame()
    {
        frame = new JFrame(WINDOW_TITLE);
        // Set size.
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));

        // Center panel.
        centerPanel = new JPanel();

        // Answer Panel. Added before to avoid conflict. Initially hidden.
        answerPanel = createAnswerPanel();
        answerPanel.setVisible(false);
        centerPanel.add(answerPanel);

        // Control Panel.
        controlPanel = createControlPanel();
        frame.add(controlPanel,BorderLayout.EAST);

        // Jeopardy Panel.
        jeopardyPanel = createJeopardyPanel();
        centerPanel.add(jeopardyPanel);

        // Add Center panel.
        frame.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(CENTER_PANEL_WIDTH,CENTER_PANEL_HEIGHT));
        // Decorations.
        createDecorations();
        frame.add(titleSplashPanel,BorderLayout.NORTH);
        // Ensure visibility.
        frame.getContentPane().setBackground(Color.WHITE);
        /*
        frame.validate();
         */
        frame.pack();
        frame.setVisible(true);

    } // end of method createFrame()

    /*
     * Create the frame of the output window with parameters defined.
     * 
     * @param questions the number of questions
     * @param categories the number of categories
     */
    private void createFrame(int questions, int categories)
    {
        frame = new JFrame(WINDOW_TITLE);
        // Set size.
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));

        // Center panel.
        centerPanel = new JPanel();

        // Answer Panel. Added before to avoid conflict. Initially hidden.
        answerPanel = createAnswerPanel();
        answerPanel.setVisible(false);
        centerPanel.add(answerPanel);

        // Control Panel.
        controlPanel = createControlPanel();
        frame.add(controlPanel,BorderLayout.EAST);

        // Jeopardy Panel.
        jeopardyPanel = createJeopardyPanel(questions, categories);
        centerPanel.add(jeopardyPanel);

        // Add Center panel.
        frame.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(CENTER_PANEL_WIDTH,CENTER_PANEL_HEIGHT));
        // Decorations.
        createDecorations();
        frame.add(titleSplashPanel,BorderLayout.NORTH);
        // Ensure visibility.
        frame.getContentPane().setBackground(Color.WHITE);
        /*
        frame.validate();
         */
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
        skeletonPanel.setLayout(new BoxLayout(skeletonPanel, BoxLayout.PAGE_AXIS));
        // Categories.
        jeopardyPanel_categoryPanel = new JPanel();
        jeopardyPanel_categoryPanel.setBackground(JEOPARDY_PANEL_COLOR);
        jeopardyPanel_label = new JLabel[CATEGORY_SIZE];
        // Create all of the category headers.
        for (int category = 0; category < CATEGORY_SIZE; category++)
        {
            // Adds and centers label in its cell.
            jeopardyPanel_label[category] = new JLabel("I am a label!" + category, JLabel.CENTER);
            // Set size. Column width to align with column.
            jeopardyPanel_label[category].setPreferredSize(new Dimension(COLUMN_WIDTH,CELL_HEIGHT));
            // Change colour, and sets opaque in order to show it!
            jeopardyPanel_label[category].setBackground(CELL_COLOR);
            jeopardyPanel_label[category].setOpaque(true);
            jeopardyPanel_label[category].setForeground(CELL_TEXT_COLOR);
            // Changes font.
            jeopardyPanel_label[category].setFont(JEOPARDY_FONT);
            // Add.
            jeopardyPanel_categoryPanel.add(jeopardyPanel_label[category]);
        } // end of for (int category = 0; category < CATEGORY_SIZE; category++)

        // Questions.
        jeopardyPanel_questionPanel = new JPanel();
        jeopardyPanel_questionPanel.setBackground(JEOPARDY_PANEL_COLOR);
        jeopardyPanel_button = new JButton[CATEGORY_SIZE][QUESTION_COUNT];
        jeopardyPanel_questionColumnPanel = new JPanel[CATEGORY_SIZE];

        // Button Listener
        JeopardyPanelButtonListener buttonListener = new JeopardyPanelButtonListener();

        // Create all the questions.
        for (int category = 0; category < CATEGORY_SIZE; category++)
        {
            // Create new horizontal JPanel to manage layout.
            jeopardyPanel_questionColumnPanel[category] = new JPanel();
            jeopardyPanel_questionColumnPanel[category].setBackground(JEOPARDY_PANEL_COLOR);
            // Create questions and add to JPanel.
            for (int question = 0; question < QUESTION_COUNT; question++)
            {
                // Make new JButton question and add to panel.
                jeopardyPanel_button[category][question] = new JButton("Coor(" + category + "," + question + ")");
                // Set size.
                jeopardyPanel_button[category][question].setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
                jeopardyPanel_button[category][question].setMargin(new Insets(0, 0, 0, 0));
                // Change colour.
                jeopardyPanel_button[category][question].setBackground(CELL_COLOR);
                jeopardyPanel_button[category][question].setForeground(CELL_TEXT_COLOR);
                // Set font.
                jeopardyPanel_button[category][question].setFont(JEOPARDY_FONT);
                // Add Action Listener.
                jeopardyPanel_button[category][question].addActionListener(buttonListener);
                // Add to panel.
                jeopardyPanel_questionColumnPanel[category].add(jeopardyPanel_button[category][question]);
            } // end of for (int question = 0; question < QUESTION_COUNT; question++)

            // Set length size to very little in order to ensure horizontal layout.
            Dimension size = jeopardyPanel_questionColumnPanel[category].getSize();
            jeopardyPanel_questionColumnPanel[category].setPreferredSize(new Dimension(COLUMN_WIDTH, (COLUMN_PADDING + CELL_HEIGHT) * question_count));
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
        skeletonPanel.setLayout(new BoxLayout(skeletonPanel, BoxLayout.PAGE_AXIS));
        // Categories.
        jeopardyPanel_categoryPanel = new JPanel();
        jeopardyPanel_categoryPanel.setBackground(JEOPARDY_PANEL_COLOR);
        jeopardyPanel_label = new JLabel[categories];
        // Create all of the category headers.
        for (int category = 0; category < categories; category++)
        {
            // Adds and centers label in its cell.
            jeopardyPanel_label[category] = new JLabel("I am a label!" + category, JLabel.CENTER);
            // Set size. Column width to align with column.
            jeopardyPanel_label[category].setPreferredSize(new Dimension(COLUMN_WIDTH,CELL_HEIGHT));
            // Change colour, and sets opaque in order to show it!
            jeopardyPanel_label[category].setBackground(CELL_COLOR);
            jeopardyPanel_label[category].setOpaque(true);
            jeopardyPanel_label[category].setForeground(CELL_TEXT_COLOR);
            // Changes font.
            jeopardyPanel_label[category].setFont(JEOPARDY_FONT);
            // Add.
            jeopardyPanel_categoryPanel.add(jeopardyPanel_label[category]);
        } // end of for (int category = 0; category < CATEGORY_SIZE; category++)

        // Questions.
        jeopardyPanel_questionPanel = new JPanel();
        jeopardyPanel_questionPanel.setBackground(JEOPARDY_PANEL_COLOR);
        jeopardyPanel_button = new JButton[categories][questions];
        jeopardyPanel_questionColumnPanel = new JPanel[categories];

        // Button Listener
        JeopardyPanelButtonListener buttonListener = new JeopardyPanelButtonListener();
        // Create all the questions.
        for (int category = 0; category < categories; category++)
        {
            // Create new horizontal JPanel to manage layout.
            jeopardyPanel_questionColumnPanel[category] = new JPanel();
            jeopardyPanel_questionColumnPanel[category].setBackground(JEOPARDY_PANEL_COLOR);
            // Create questions and add to JPanel.
            for (int question = 0; question < questions; question++)
            {
                // Make new JButton question and add to panel.
                jeopardyPanel_button[category][question] = new JButton("Coor(" + category + "," + question + ")");
                // Set size.
                jeopardyPanel_button[category][question].setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
                jeopardyPanel_button[category][question].setMargin(new Insets(0, 0, 0, 0));
                // Change colour.
                jeopardyPanel_button[category][question].setBackground(CELL_COLOR);
                jeopardyPanel_button[category][question].setForeground(CELL_TEXT_COLOR);
                // Set font.
                jeopardyPanel_button[category][question].setFont(JEOPARDY_FONT);
                // Add Action Listener.
                jeopardyPanel_button[category][question].addActionListener(buttonListener);
                // Add to panel.
                jeopardyPanel_questionColumnPanel[category].add(jeopardyPanel_button[category][question]);
            } // end of for (int question = 0; question < QUESTION_COUNT; question++)
            // Set length size to very little in order to ensure horizontal layout.
            jeopardyPanel_questionColumnPanel[category].setPreferredSize(new Dimension(COLUMN_WIDTH, (COLUMN_PADDING + CELL_HEIGHT) * question_count));
            // Add back to questionPanel.
            jeopardyPanel_questionPanel.add(jeopardyPanel_questionColumnPanel[category]);
        } // end of for (int category = 0; category < CATEGORY_COUNT; category++)

        // Build onto skeleton.
        // Note, redo layout with flow layout manager.
        skeletonPanel.add(jeopardyPanel_categoryPanel,BorderLayout.NORTH);
        skeletonPanel.add(jeopardyPanel_questionPanel,BorderLayout.CENTER);

        return skeletonPanel;
    } // end of method createJeopardyPanel()
    /*
     * Create the control panel.
     */
    private JPanel createControlPanel()
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();
        skeletonPanel.setBackground(CONTROL_PANEL_COLOR);

        // ActionListener
        ControlPanelButtonListener actionListener = new ControlPanelButtonListener();

        // Quit Button.
        controlPanel_quitButton = new JButton("Quit");
        controlPanel_quitButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_WIDTH, CONTROL_PANEL_BUTTON_HEIGHT));
        controlPanel_quitButton.setBackground(CONTROL_PANEL_BUTTON_COLOR);
        controlPanel_quitButton.setForeground(CONTROL_PANEL_BUTTON_TEXT_COLOR);
        controlPanel_quitButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_quitButton);
        // Reset Button.
        controlPanel_resetButton = new JButton("Reset");
        controlPanel_resetButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_WIDTH, CONTROL_PANEL_BUTTON_HEIGHT));
        controlPanel_resetButton.setBackground(CONTROL_PANEL_BUTTON_COLOR);
        controlPanel_resetButton.setForeground(CONTROL_PANEL_BUTTON_TEXT_COLOR);
        controlPanel_resetButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_resetButton);
        // Load Button.
        controlPanel_loadButton = new JButton("Load");
        controlPanel_loadButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_WIDTH, CONTROL_PANEL_BUTTON_HEIGHT));
        controlPanel_loadButton.setBackground(CONTROL_PANEL_BUTTON_COLOR);
        controlPanel_loadButton.setForeground(CONTROL_PANEL_BUTTON_TEXT_COLOR);
        controlPanel_loadButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_loadButton);
        // Help Button.
        controlPanel_helpButton = new JButton("Help");
        controlPanel_helpButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_WIDTH, CONTROL_PANEL_BUTTON_HEIGHT));
        controlPanel_helpButton.setBackground(CONTROL_PANEL_BUTTON_COLOR);
        controlPanel_helpButton.setForeground(CONTROL_PANEL_BUTTON_TEXT_COLOR);
        controlPanel_helpButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_helpButton);
        // Settings Button.
        controlPanel_settingsButton = new JButton("Settings");
        controlPanel_settingsButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_WIDTH, CONTROL_PANEL_BUTTON_HEIGHT));
        controlPanel_settingsButton.setBackground(CONTROL_PANEL_BUTTON_COLOR);
        controlPanel_settingsButton.setForeground(CONTROL_PANEL_BUTTON_TEXT_COLOR);
        controlPanel_settingsButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_settingsButton);
        // Manually Switch Player Button.
        controlPanel_switchPlayerButton = new JButton("Switch Player");
        controlPanel_switchPlayerButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_WIDTH, CONTROL_PANEL_BUTTON_HEIGHT));
        controlPanel_switchPlayerButton.setBackground(CONTROL_PANEL_BUTTON_COLOR);
        controlPanel_switchPlayerButton.setForeground(CONTROL_PANEL_BUTTON_TEXT_COLOR);
        controlPanel_switchPlayerButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_switchPlayerButton);

        // Set small size in order to have horizontal layout.
        skeletonPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH,CONTROL_PANEL_HEIGHT));

        return skeletonPanel;
    } // end of method createControlPanel()

    /*
     * Create the answer panel.
     */
    private JPanel createAnswerPanel()
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();
        skeletonPanel.setBackground(ANSWER_PANEL_COLOR);

        // Titlecard JLabel.
        answerPanel_questionLabel = new JLabel("I am the question!");
        skeletonPanel.add(answerPanel_questionLabel, BorderLayout.NORTH);

        // Create the answers.
        answerPanel_answerButton = new JButton[answer_count];
        for (int answer = 0; answer < answer_count; answer++)
        {
            answerPanel_answerButton[answer] = new JButton("I am an answer!");
            skeletonPanel.add(answerPanel_answerButton[answer], BorderLayout.CENTER);
        } // end of for (int answer = 0; answer < answer_count; answer++)
        // Set size.
        skeletonPanel.setPreferredSize(new Dimension(ANSWER_PANEL_WIDTH, ANSWER_PANEL_HEIGHT));

        return skeletonPanel;
    } // end of method createAnswerPanel()

    /*
     * Create the other decorations.
     */
    private void createDecorations()
    {
        // Title.
        titleSplashPanel = new JPanel();
        titleSplashPanel_label = new JLabel("Jeopardy");
        titleSplashPanel_label.setFont(JEOPARDY_TITLE_FONT);
        titleSplashPanel.add(titleSplashPanel_label);
        // Scoreboard.

    } // end of method createDecorations()
    /*
     * Do the effects.
     */
    private void screenEffect()
    {

    } // end of method screenEffect()

    /*
     * Private class that handles control panel processing.
     */
    private class ControlPanelButtonListener implements ActionListener
    {
        // Instance.
        private JFileChooser fileChooser;

        /**
         * Creates a new Control Panel.
         */
        public ControlPanelButtonListener()
        {
            fileChooser = new JFileChooser();
        } // end of constructor ControlPanelButtonListener()
        /**
         * Handles control panel effects.
         * 
         * @param event ActionEvent that is fired
         */
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();

            // Handle clicks
            if(source == controlPanel_quitButton)
            {
                // Popup to ask user if they are sure.
                int sure = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?");
                if (JOptionPane.YES_OPTION == sure)
                {
                    System.exit(0);
                } // end of if (JOptionPane.YES_OPTION == sure)
            }
            else if(source == controlPanel_resetButton)
            {
                // Popup to ask user if they are sure.
                int sure = JOptionPane.showConfirmDialog(frame, "Are you sure you want to reset the game?");

                if (JOptionPane.YES_OPTION == sure)
                {
                    // Reset Data.

                    // Reset Jeopardy board.
                    // Proper values for data---
                    reloadJeopardy(answer_count,question_count,category_size,"");
                    // Reset everything else.
                } // end of if (JOptionPane.YES_OPTION == sure)
            }
            else if(source == controlPanel_loadButton)
            {
                // Choose a file.
                int fileReturned = fileChooser.showOpenDialog(frame);
                // Load data.
                if (fileReturned == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                } // end of if (fileReturned == JFileChooser.APPROVE_OPTION)
                // Reload Jeopardy.

            }
            else if (source == controlPanel_settingsButton)
            {
                // dialog popup settings
            }
            else if (source == controlPanel_switchPlayerButton)
            {
                // switch player
            } // end of if(source == controPanel)
        } // end of method actionPerformed(ActionEvent event)
    } // end of method ControlPanelButtonListener()

    /*
     * Private class that handles jeopardy panel processing.
     */
    private class JeopardyPanelButtonListener implements ActionListener
    {
        /**
         * Creates a default button listener.
         */
        public JeopardyPanelButtonListener()
        {

        } // end of constructor JeopardyPanelButtonListener()

        /**
         * Handles jeopardy panel effects.
         * 
         * @param event ActionEvent that is fired
         */
        public void actionPerformed(ActionEvent event)
        {
            // Do action sequence for clicking on button.
            JButton sourceButton = ((JButton)event.getSource());
            // Disable button and colorize to reflect that.
            //sourceButton.setForeground(CELL_COLOR);
            // Find it.
            for (int category = 0; category < CATEGORY_SIZE; category++)
            {
                for (int question = 0; question < QUESTION_COUNT; question++)
                {
                    if (sourceButton == jeopardyPanel_button[category][question])
                    {
                        // Flash Red.
                        jeopardyPanel_label[category].setBackground(FLASH_COLOR);

                        // Question Dialog.

                        // Disable button and colorize to reflect that.
                        jeopardyPanel_button[category][question].setEnabled(false);
                        jeopardyPanel_button[category][question].setForeground(CELL_COLOR);
                        // This is nesscary to ensure disablement, as when it is set disabled, the text color does not change!
                        jeopardyPanel_button[category][question].setUI(new ButtonUI() 
                            {
                                protected Color getDisabledTextColor() 
                                {
                                    return CELL_COLOR;
                                } // end of getDisabledTextColor() 
                            });
                        // Reset when done
                        jeopardyPanel_label[category].setBackground(CELL_COLOR);

                        // Finished.
                        return;
                    }
                } // end of for (int question = 0; question < QUESTION_COUNT; question++)
            } // end of for (int category = 0; category < CATEGORY_SIZE; category++)

        } // end of method actionPerformed(ActionEvent event)

    } // end of method JeopardyPanelButtonListener

} // end of class OutputWindow
