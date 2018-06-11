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
import javax.swing.JComboBox;
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
 * Settings, helps to customize various aspects of the program.
 *
 * @author Frank Lai
 * @version 2018-06-08
 */
public class SettingsDialog extends JDialog
{
    private static final int DIALOG_WIDTH = 500;
    private static final int DIALOG_HEIGHT = 400;

    private static final String TITLE = "Settings";
    private static final boolean MODALITY_TYPE = true;

    // GUI elements.
    private JPanel parameterPanel;
    private JComboBox parameterPanel_questionNumberList;
    private JLabel parameterPanel_questionNumberList_label;
    private JComboBox parameterPanel_answerNumberList;
    private JLabel parameterPanel_answerNumberList_label;

    private JPanel audioPanel;

    private JPanel themePanel;

    private JPanel controlPanel;
    private JButton controlPanel_applyButton;
    private JButton controlPanel_cancelButton;
    // Instance.
    private int previousQuestionNumberList;
    private int previousAnswerNumberList;

    /**
     * Creates a new settings dialog.
     */
    public SettingsDialog()
    {
        setTitle(TITLE);
        setModal(MODALITY_TYPE);
        setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        setLayout(new BorderLayout());

        // Create panels.
        parameterPanel = createParameterPanel();
        audioPanel = createAudioPanel();
        themePanel = createThemePanel();
        controlPanel = createControlPanel();

        add(parameterPanel,BorderLayout.WEST);
        add(audioPanel,BorderLayout.CENTER);
        add(themePanel,BorderLayout.EAST);
        add(controlPanel,BorderLayout.SOUTH);
        // Ensure final validation.
        pack();
    } // end of constructor SettingsDialog()

    /**
     * Revisibilizes the dialog.
     */
    public void showDialog()
    {
        setVisible(true);
    } // end of method showDialog()

    /**
     * Invisibilizes the dialog.
     */
    public void hideDialog()
    {
        setVisible(false);
    } // end of method hideDialog()

    /**
     * Returns the number of questions selected by the dialog.
     * 
     * @return the number of questions.
     */
    public int getQuestionCount()
    {
        return parameterPanel_questionNumberList.getSelectedIndex();
    } // end of getQuestionCount()

    /**
     * Returns the number of answers selected by the dialog.
     * 
     * @return the number of answers.
     */
    public int getAnswerCount()
    {
        return parameterPanel_answerNumberList.getSelectedIndex();
    } // end of getQuestionCount()

    /*
     * Creates a new parameter panel.
     */
    private JPanel createParameterPanel()
    {
        JPanel skeletonPanel = new JPanel();
        // Number of questions and answers.
        String[] parameterPossibleText = {"One","Two","Three","Four","Five","Six"};
        previousQuestionNumberList = 4;
        previousAnswerNumberList = 4;
        
        // Questions.
        parameterPanel_questionNumberList_label = new JLabel("Questions");
        skeletonPanel.add(parameterPanel_questionNumberList_label);
        parameterPanel_questionNumberList = new JComboBox(parameterPossibleText);
        parameterPanel_questionNumberList.setSelectedIndex(previousQuestionNumberList);
        skeletonPanel.add(parameterPanel_questionNumberList);

        // Answers.
        parameterPanel_answerNumberList_label = new JLabel("Answers");
        skeletonPanel.add(parameterPanel_answerNumberList_label);
        parameterPanel_answerNumberList = new JComboBox(parameterPossibleText);
        parameterPanel_answerNumberList.setSelectedIndex(previousAnswerNumberList);
        skeletonPanel.add(parameterPanel_answerNumberList);

        // Cool group borders.
        skeletonPanel.setBorder(BorderFactory.createTitledBorder("Jeopardy Paremeters"));

        return skeletonPanel;
    } // end of method createParameterPanel()

    /*
     * Creates a new audio panel.
     */
    private JPanel createAudioPanel()
    {
        JPanel skeletonPanel = new JPanel();

        // Cool group borders.
        skeletonPanel.setBorder(BorderFactory.createTitledBorder("Audio"));

        return skeletonPanel;
    } // end of method createParameterPanel()

    /*
     * Creates a new theme panel.
     */
    private JPanel createThemePanel()
    {
        JPanel skeletonPanel = new JPanel();

        // Cool group borders.
        skeletonPanel.setBorder(BorderFactory.createTitledBorder("Theme"));

        return skeletonPanel;
    } // end of method createParameterPanel()

    /*
     * Creates a new control panel.
     */
    private JPanel createControlPanel()
    {
        JPanel skeletonPanel = new JPanel();

        SettingsDialog_controlPanelListener actionListener = new SettingsDialog_controlPanelListener();
        // Apply button.
        controlPanel_applyButton = new JButton("Apply Changes");
        controlPanel_applyButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_applyButton);
        // Cancel button.
        controlPanel_cancelButton = new JButton("Cancel");
        controlPanel_cancelButton.addActionListener(actionListener);
        skeletonPanel.add(controlPanel_cancelButton);

        return skeletonPanel;
    } // end of method createControlPanel()

    /*
     * Hide and clears all options to default.
     */
    private void hideAndClear()
    {
        setVisible(false);

        // Clear.
        parameterPanel_questionNumberList.setSelectedIndex(previousQuestionNumberList);
        parameterPanel_answerNumberList.setSelectedIndex(previousAnswerNumberList);
    } // end of method hideAndClear()

    /*
     * ActionListener class that handles the submit or cancel buttons.
     */
    private class SettingsDialog_controlPanelListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();
            if (source == controlPanel_applyButton)
            {
                // Apply options, simply hide.
                previousQuestionNumberList = getQuestionCount();
                previousAnswerNumberList = getAnswerCount();
                hideDialog();
            }
            else if (source == controlPanel_cancelButton)
            {
                // Hide and then reset everything.
                hideAndClear();
            } // end of if (source == controlPanel_applyButton)
        }// end of ActionPerformed(ActionEvent event)
    } // end of class SettingsDialog_controlPanelListener()

} // end of class SettingsDialog extends JDialog
