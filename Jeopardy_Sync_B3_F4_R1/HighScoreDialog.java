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
 * Displays the highscore.
 *
 * @author Frank Lai
 * @version 2018-06-08
 */
public class HighScoreDialog extends JDialog implements ActionListener
{
    private static final String TITLE = "Highscore";
    //private static final Dialog.ModalityType modalityType;
    // Instance.
    
    /**
     * Creates a new highscore JDialog.
     */
    public HighScoreDialog()//Window owner)
    {
        
        
    } // end of method HighScoreDialog()
    
    public void actionPerformed(ActionEvent event) 
    {
        
    } // end of method actionPerformed(ActionEvent event) 
    
    /**
     * Hides the dialog.
     */
    public void setInvisible()
    {
        setVisible(false);
    } // end of method hide()
} // end of class HighScoreDialog
