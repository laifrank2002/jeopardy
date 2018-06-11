import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Displays the highscores.
 *
 * @author Frank Lai
 * @version 2018-06-08
 */
public class HighScoreDialog extends JDialog implements ActionListener
{
    private static final int DIALOG_WIDTH = 200;
    private static final int DIALOG_HEIGHT = 300;
    
    private static final String TITLE = "Highscore";
    private static final boolean MODALITY_TYPE = false;
    // GUI Elements.
    private JLabel[] highScore;
    private JButton quitButton;
    private HighScore score;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    /**
     * Creates a new highscore JDialog.
     */
    public HighScoreDialog()
    {
        setTitle(TITLE);
        setModal(MODALITY_TYPE);
        
        mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        setPreferredSize(new Dimension(DIALOG_WIDTH,DIALOG_HEIGHT));

        // Ensures won't open at minimum size.
        pack();
    } // end of method HighScoreDialog()
    
    /**
     * Responds to quit button.
     */
    public void actionPerformed(ActionEvent event) 
    {
        if (event.getSource() == quitButton)
        {
            setInvisible();
        } // end of method if (event.getSource() == quitButton)
    } // end of method actionPerformed(ActionEvent event) 
    
    /**
     * Hides the dialog.
     */
    public void setInvisible()
    {
        setVisible(false);
    } // end of method hide()
    
    /**
     * Revisibilizes the dialog.
     */
    public void showDialog()
    {
        setVisible(true);
    } // end of method show()
    
    /**
     * Shows the dialog and reloads all data again.
     */
    public void reloadDialog()
    {
        // Makes a new one in order to update data.
        score = new HighScore();
        // Reload text.
        for (int index = 0; index < highScore.length; index++)
        {
            highScore[index].setText(score.getName(index) + ": " + score.getHighScore(index));
        } // end of for (int index = 0; index < highScore.length; index++)
    } // end of method reloadDialog()
    
    /*
     * Creates the main panel.
     */
    private JPanel createMainPanel()
    {
        JPanel skeletonPanel = new JPanel();
        skeletonPanel.setLayout(new BoxLayout(skeletonPanel, BoxLayout.PAGE_AXIS));
        
        score = new HighScore();
        // Create new scores.
        highScore = new JLabel[HighScore.NUMBER_OF_HIGHSCORES];
        for (int index = 0; index < highScore.length; index++)
        {
            highScore[index] = new JLabel((index + 1) + "." + score.getName(index) + ": " + score.getHighScore(index));
            skeletonPanel.add(highScore[index]);
        } // end of for (int index = 0; index < highScore.length; index++)
        
        // Cool group borders.
        skeletonPanel.setBorder(BorderFactory.createTitledBorder("Highscores"));
        
        return skeletonPanel;
    } // end of method createMainPanel()
    /*
     * Creates the button panel.
     */
    private JPanel createButtonPanel()
    {
        JPanel skeletonPanel = new JPanel();
        
        // Quit Button.
        quitButton = new JButton("Exit");
        quitButton.addActionListener(this);
        skeletonPanel.add(quitButton);
        
        return skeletonPanel;
    } // end of method createButtonPanel()
} // end of class HighScoreDialog
