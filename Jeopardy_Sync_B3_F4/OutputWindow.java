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
    public static final int ANSWER_COUNT = 5;
    public static final int QUESTION_COUNT = 6;
    public static final int CATEGORY_SIZE = 6;

    public static final int JEOPARDY_FONT_SIZE = 24;
    public static final int JEOPARDY_TITLE_FONT_SIZE = 108;

    public static final Font JEOPARDY_FONT = FontLoader.getFont("Fonts\\Jeopardy\\jeopardy.ttf",JEOPARDY_FONT_SIZE);
    public static final Font JEOPARDY_TITLE_FONT = FontLoader.getFont("Fonts\\Jeopardy\\jeopardy.ttf",JEOPARDY_TITLE_FONT_SIZE);
    // Window constants.
    public static final int WINDOW_WIDTH = 1300;
    public static final int WINDOW_HEIGHT = 768;
    public static final String WINDOW_TITLE = "Jeopardy";
    // Answer Panel constants.
    public static final int ANSWER_PANEL_QUESTION_LABEL_WIDTH = 500;
    public static final int ANSWER_PANEL_QUESTION_LABEL_HEIGHT = 300;
    public static final int ANSWER_PANEL_BUTTON_PANEL_WIDTH = 250;
    public static final int ANSWER_PANEL_BUTTON_PANEL_HEIGHT = 400;
    public static final int ANSWER_PANEL_HEIGHT = 375;
    public static final int ANSWER_PANEL_WIDTH = 150 * CATEGORY_SIZE;
    public static final int ANSWER_PANEL_BUTTON_HEIGHT = 60;
    public static final int ANSWER_PANEL_BUTTON_WIDTH = 250;
    public static final Color ANSWER_PANEL_COLOR = Color.BLACK;
    public static final Color ANSWER_PANEL_QUESTION_TEXT_COLOR = Color.WHITE;
    public static final Color ANSWER_PANEL_BUTTON_COLOR = Color.BLUE;
    public static final Color ANSWER_PANEL_BUTTON_TEXT_COLOR = Color.WHITE;
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
    public static final Color JEOPARDY_PANEL_LABEL_COLOR = Color.WHITE;
    public static final Color CELL_TEXT_COLOR = Color.YELLOW;
    public static final Color CELL_COLOR = Color.BLUE;
    public static final Color DISABLED_CELL_COLOR = Color.BLACK;
    public static final Color FLASH_COLOR = Color.RED;
    public static final Color JEOPARDY_PANEL_COLOR = Color.BLACK;
    // Scoreboard Panel constants.
    public static final int SCOREBOARD_PANEL_WIDTH = 200;
    public static final int SCOREBOARD_PANEL_HEIGHT = 375;
    public static final int SCOREBOARD_PANEL_SCORE_WIDTH = 150;
    public static final int SCOREBOARD_PANEL_SCORE_HEIGHT = 65;
    public static final Color SCOREBOARD_PANEL_COLOR = Color.BLACK;
    public static final Color SCOREBOARD_PANEL_SCORE_COLOR = Color.WHITE;
    // GUI components.
    private JFrame frame;

    private JPanel answerPanel;
    private JPanel answerPanel_buttonPanel;
    private JLabel answerPanel_questionLabel;
    private JButton[] answerPanel_answerButton;

    private JPanel centerPanel;
    private JPanel centerPanel_categoryPanel;
    private JLabel[] centerPanel_label;

    private JPanel controlPanel;
    private JButton controlPanel_quitButton;
    private JButton controlPanel_resetButton;
    private JButton controlPanel_loadButton; // also new round button
    private JButton controlPanel_helpButton;
    private JButton controlPanel_settingsButton;
    private JButton controlPanel_switchPlayerButton;
    private JButton controlPanel_finishGameButton;

    private JPanel jeopardyPanel;
    //private JPanel jeopardyPanel_categoryPanel;
    //private JLabel[] jeopardyPanel_label;
    private JPanel jeopardyPanel_questionPanel;
    private JPanel [] jeopardyPanel_questionColumnPanel;
    private JButton[][] jeopardyPanel_button;

    private JPanel scoreboardPanel;
    private JLabel[] scoreboardPanel_score;
    private JLabel scoreboardPanel_currentPlayer;

    private JPanel titleSplashPanel;
    private JLabel titleSplashPanel_label;
    // Instance fields.
    private int answer_count;
    private int category_size;
    private int question_count;

    private boolean isJeopardyHidden;

    // Less global.
    private String correctAnswer;
    private int currentPrizeValue;
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
     */
    public void reloadJeopardy(int answers, int questions, int categories)
    {
        // Remove from frame.
        centerPanel.remove(jeopardyPanel);

        // Rebuild new frame.
        jeopardyPanel = createJeopardyPanel(questions,categories);

        // Readd to frame.
        centerPanel.add(jeopardyPanel,BorderLayout.CENTER);
        isJeopardyHidden = false;
        answerPanel.setVisible(false);
        jeopardyPanel.setVisible(true);
        centerPanel.repaint();
        jeopardyPanel.repaint();

        frame.add(centerPanel);
        frame.pack();
        frame.validate();
        frame.repaint();

    } // end of method reloadJeopardy(int answers, int questions, int categories, String dataFile)

    /**
     * Destroys and reloads the scorepanel without reloading the jeopardy reload being reloaded.
     * 
     * @param players the new number of players
     */
    public void reloadScore(int players)
    {

    } // end of method reloadScore(int players)

    /**
     * Updates the scores of the scorepanel.
     */
    public void updateScore()
    {
        scoreboardPanel_currentPlayer.setText("Current Player: " + Jeopardy.getPlayerName(Jeopardy.currentPlayer) + " " + (Jeopardy.currentPlayer + 1));
        for (int playerIndex = 0; playerIndex < Jeopardy.player_count; playerIndex++)
        {
            scoreboardPanel_score[playerIndex].setText(Jeopardy.getPlayerName(playerIndex) + ": " + Jeopardy.getScore(playerIndex));
        } // end of for (int playerIndex = 0; playerIndex < Jeopardy.player_count; playerIndex++) 
    } // end of updateScore()

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
        centerPanel = createCenterPanel(QUESTION_COUNT,CATEGORY_SIZE);

        // Control Panel.
        controlPanel = createControlPanel();
        frame.add(controlPanel,BorderLayout.EAST);

        // Score Panel.
        scoreboardPanel = createScoreboardPanel();
        frame.add(scoreboardPanel, BorderLayout.WEST);

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
        centerPanel = createCenterPanel(questions,categories);

        // Score Panel.
        scoreboardPanel = createScoreboardPanel();
        frame.add(scoreboardPanel, BorderLayout.WEST);

        // Control Panel.
        controlPanel = createControlPanel();
        frame.add(controlPanel,BorderLayout.EAST);

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
     * Create the central panel.
     * 
     * @param questions the number of questions
     * @param categories the number of categories
     */
    private JPanel createCenterPanel(int questions, int categories)
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();
        skeletonPanel.setLayout(new BoxLayout(skeletonPanel, BoxLayout.PAGE_AXIS));

        // Categories.
        centerPanel_categoryPanel = new JPanel();
        centerPanel_categoryPanel.setBackground(JEOPARDY_PANEL_COLOR);
        centerPanel_label = new JLabel[categories];
        // Create all of the category headers.
        for (int category = 0; category < categories; category++)
        {
            // Adds and centers label in its cell.
            centerPanel_label[category] = new JLabel(FileIO.getCategory(category), JLabel.CENTER);
            // Set size. Column width to align with column.
            centerPanel_label[category].setPreferredSize(new Dimension(COLUMN_WIDTH,CELL_HEIGHT));
            // Change colour, and sets opaque in order to show it!
            centerPanel_label[category].setBackground(CELL_COLOR);
            centerPanel_label[category].setOpaque(true);
            centerPanel_label[category].setForeground(JEOPARDY_PANEL_LABEL_COLOR);
            // Changes font.
            centerPanel_label[category].setFont(JEOPARDY_FONT);
            // Add.
            centerPanel_categoryPanel.add(centerPanel_label[category]);
        } // end of for (int category = 0; category < CATEGORY_SIZE; category++)
        skeletonPanel.add(centerPanel_categoryPanel,BorderLayout.NORTH);

        // Answer Panel. Added before to avoid conflict. Initially hidden.
        answerPanel = createAnswerPanel();
        answerPanel.setVisible(false);
        skeletonPanel.add(answerPanel);

        // Jeopardy Panel.
        jeopardyPanel = createJeopardyPanel(questions, categories);
        skeletonPanel.add(jeopardyPanel);

        return skeletonPanel;
    } // end of method createCenterPanel()

    /*
     * Create the jeopardy panel.
     */
    private JPanel createJeopardyPanel()
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();
        skeletonPanel.setLayout(new BoxLayout(skeletonPanel, BoxLayout.PAGE_AXIS));

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
                jeopardyPanel_button[category][question] = new JButton(Jeopardy.MONEY_PREFIX + Integer.toString(Jeopardy.MONEY_INTERVAL * (question + 1)));
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
                jeopardyPanel_button[category][question] = new JButton(Jeopardy.MONEY_PREFIX + Integer.toString(Jeopardy.MONEY_INTERVAL * (question + 1)));
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

        AnswerButtonListener actionListener = new AnswerButtonListener();
        // Titlecard JLabel.
        answerPanel_questionLabel = new JLabel("I am the question!");
        // Set as HTML to enable wrapping and set size in order to allow wrapping.
        answerPanel_questionLabel.setPreferredSize(new Dimension(ANSWER_PANEL_QUESTION_LABEL_WIDTH, ANSWER_PANEL_QUESTION_LABEL_HEIGHT));
        answerPanel_questionLabel.setText("<html>"+ "" +"</html>");
        answerPanel_questionLabel.setFont(JEOPARDY_FONT);
        answerPanel_questionLabel.setForeground(ANSWER_PANEL_QUESTION_TEXT_COLOR);
        skeletonPanel.add(answerPanel_questionLabel, BorderLayout.NORTH);

        // Create the answers.
        answerPanel_answerButton = new JButton[answer_count];
        answerPanel_buttonPanel = new JPanel();
        answerPanel_buttonPanel.setBackground(ANSWER_PANEL_COLOR);
        answerPanel_buttonPanel.setPreferredSize(new Dimension(ANSWER_PANEL_BUTTON_PANEL_WIDTH, ANSWER_PANEL_BUTTON_PANEL_HEIGHT));
        for (int answer = 0; answer < answer_count; answer++)
        {
            answerPanel_answerButton[answer] = new JButton("I am an answer!");
            answerPanel_answerButton[answer].setBackground(ANSWER_PANEL_BUTTON_COLOR);
            answerPanel_answerButton[answer].setForeground(ANSWER_PANEL_BUTTON_TEXT_COLOR);
            answerPanel_answerButton[answer].setPreferredSize(new Dimension(ANSWER_PANEL_BUTTON_WIDTH,ANSWER_PANEL_BUTTON_HEIGHT));
            answerPanel_answerButton[answer].addActionListener(actionListener);
            answerPanel_buttonPanel.add(answerPanel_answerButton[answer], BorderLayout.CENTER);
        } // end of for (int answer = 0; answer < answer_count; answer++)
        skeletonPanel.add(answerPanel_buttonPanel);
        // Set size.
        skeletonPanel.setPreferredSize(new Dimension(ANSWER_PANEL_WIDTH, ANSWER_PANEL_HEIGHT));

        return skeletonPanel;
    } // end of method createAnswerPanel()

    /*
     * Create the scoreboard panel.
     */
    public JPanel createScoreboardPanel()
    {
        // Temporary JPanel.
        JPanel skeletonPanel = new JPanel();
        skeletonPanel.setBackground(SCOREBOARD_PANEL_COLOR);

        // Current player display.
        scoreboardPanel_currentPlayer = new JLabel("Current Player: " + Jeopardy.getPlayerName(Jeopardy.currentPlayer) + " " + (Jeopardy.currentPlayer + 1));
        scoreboardPanel_currentPlayer.setForeground(SCOREBOARD_PANEL_SCORE_COLOR);
        scoreboardPanel_currentPlayer.setPreferredSize(new Dimension(SCOREBOARD_PANEL_SCORE_WIDTH,SCOREBOARD_PANEL_SCORE_HEIGHT));
        skeletonPanel.add(scoreboardPanel_currentPlayer);
        // Create scores.
        scoreboardPanel_score = new JLabel[Jeopardy.player_count];
        for (int playerIndex = 0; playerIndex < Jeopardy.player_count; playerIndex++)
        {
            scoreboardPanel_score[playerIndex] = new JLabel(Jeopardy.getPlayerName(playerIndex) + ": " + Jeopardy.getScore(playerIndex));
            scoreboardPanel_score[playerIndex].setForeground(SCOREBOARD_PANEL_SCORE_COLOR);
            scoreboardPanel_score[playerIndex].setPreferredSize(new Dimension(SCOREBOARD_PANEL_SCORE_WIDTH,SCOREBOARD_PANEL_SCORE_HEIGHT));
            skeletonPanel.add(scoreboardPanel_score[playerIndex]);
        } // end of for (int playerIndex = 0; playerIndex < Jeopardy.player_count; playerIndex++) 
        // Set size.
        skeletonPanel.setPreferredSize(new Dimension(SCOREBOARD_PANEL_WIDTH, SCOREBOARD_PANEL_HEIGHT));

        return skeletonPanel;
    } // end of method createScoreboardPanel()
    /*
     * Set the question of the answer panel.
     */
    private void setAnswer(int category, int question)
    {
        // Set texts
        // Set as HTML to enable wrapping.
        answerPanel_questionLabel.setText("<html>"+FileIO.getAnswer(category,question)+"</html>");
        for (int answer = 0; answer < answer_count; answer++)
        {
            answerPanel_answerButton[answer].setText(FileIO.getQuestion(category,question,answer));
        } // end of for (int answer = 0; answer < answer_count; answer++)

    } // end of method setAnswer()

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
                    Jeopardy.reloadPlayerData();
                    updateScore();
                    // Reset Jeopardy board.
                    // Proper values for data---
                    reloadJeopardy(answer_count,question_count,category_size);
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
                    // Reload Jeopardy.
                    Jeopardy.loadNewJeopardy(file);
                } // end of if (fileReturned == JFileChooser.APPROVE_OPTION)

            }
            else if (source == controlPanel_settingsButton)
            {
                // dialog popup settings
            }
            else if (source == controlPanel_switchPlayerButton)
            {
                // switch player
                Jeopardy.changePlayer();
                updateScore();
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
            // Find it.
            for (int category = 0; category < category_size; category++)
            {
                for (int question = 0; question < question_count; question++)
                {
                    if (sourceButton == jeopardyPanel_button[category][question])
                    {
                        // Flash Red.
                        centerPanel_label[category].setBackground(FLASH_COLOR);
                        // Question Dialog.
                        currentPrizeValue = (question + 1) * Jeopardy.MONEY_INTERVAL;
                        switchJeopardy();
                        setAnswer(category,question);
                        correctAnswer = FileIO.getCorrectAnswer(category,question);

                        // Disable button and colorize to reflect that.
                        jeopardyPanel_button[category][question].setEnabled(false);
                        jeopardyPanel_button[category][question].setBackground(DISABLED_CELL_COLOR);
                        jeopardyPanel_button[category][question].setForeground(DISABLED_CELL_COLOR);
                        // This is nesscary to ensure disablement, as when it is set disabled, the text color does not change!
                        jeopardyPanel_button[category][question].setUI(new ButtonUI() 
                            {
                                protected Color getDisabledTextColor() 
                                {
                                    return DISABLED_CELL_COLOR;
                                } // end of getDisabledTextColor() 
                            });

                        // Finished.
                        return;
                    }
                } // end of for (int question = 0; question < QUESTION_COUNT; question++)
            } // end of for (int category = 0; category < CATEGORY_SIZE; category++)

        } // end of method actionPerformed(ActionEvent event)

    } // end of method JeopardyPanelButtonListener

    /*
     * Private class that handles answer panel processing. 
     */
    private class AnswerButtonListener implements ActionListener
    {
        /**
         * Creates a default button listener.
         */
        public AnswerButtonListener()
        {

        } // end of constructor JeopardyPanelButtonListener()

        /**
         * Handles jeopardy panel effects.
         * 
         * @param event ActionEvent that is fired
         */
        public void actionPerformed(ActionEvent event)
        {
            JButton sourceButton = ((JButton)event.getSource());

            // Find it.
            for (int answer = 0; answer < answer_count; answer ++)
            {
                if (sourceButton == answerPanel_answerButton[answer])
                {
                    // Are you sure?
                    int returnNumber = JOptionPane.showConfirmDialog(answerPanel, "Are you sure that's the right answer?");
                    if (returnNumber != JOptionPane.YES_OPTION) return;
                    // Is the answer correct?
                    if(sourceButton.getText().equals(correctAnswer))
                    {
                        // Add score, do victory sound.
                        Jeopardy.addScore(currentPrizeValue,Jeopardy.currentPlayer);
                        Jeopardy.changePlayer();
                        switchJeopardy();
                        updateScore();
                        //System.out.println("yay!");
                        // Reset high labels.
                        for (int category = 0; category < category_size; category++)
                        {
                            centerPanel_label[category].setBackground(CELL_COLOR);
                        } // end of for (int category = 0; category < category_size; category++)
                    } 
                    else
                    {
                        // Deduct score(according to settings), do failure sound.
                        Jeopardy.subtractScore(currentPrizeValue,Jeopardy.currentPlayer);
                        Jeopardy.changePlayer();
                        switchJeopardy();
                        updateScore();
                        //System.out.println("nay!");
                        // Reset high labels.
                        for (int category = 0; category < category_size; category++)
                        {
                            centerPanel_label[category].setBackground(CELL_COLOR);
                        } // end of for (int category = 0; category < category_size; category++)
                    } // end of if(sourceButton.getText().equals(correctAnswer)) 
                    // We're done when we got the button.
                    return;
                } // end of if (sourceButton == answerPanel_answerButton[answer])
            } // end of for(int answer = 0; answer < answer_count; answer ++)
        } // end of method actionPerformed(ActionEvent event)

    } // end of class AnswerButtonListener

} // end of class OutputWindow
