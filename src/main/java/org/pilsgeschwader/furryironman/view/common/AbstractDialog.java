package org.pilsgeschwader.furryironman.view.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author binarygamura
 */
public class AbstractDialog extends JDialog
{
    protected final ButtonPanel buttonPanel;
    
    private final JProgressBar progressbar;
    
    public AbstractDialog(String title, JFrame parent)
    {
        super(parent);
        setTitle(title);
        setModal(true);
        setResizable(false);
        buttonPanel = new ButtonPanel();
        progressbar = new JProgressBar();
        progressbar.setStringPainted(true);
        progressbar.setString("");
        
        add(progressbar, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void addDefaultCloseButton()
    {
        JButton closeButton = new JButton("close");
        closeButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        buttonPanel.addButton(closeButton);
    }
    
    public AbstractDialog(String title, JDialog parent)
    {
        super(parent);
        setTitle(title);
        setModal(true);
        buttonPanel = new ButtonPanel();
        progressbar = new JProgressBar();
        progressbar.setStringPainted(true);
        
        add(progressbar, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void startProgressBar(String message)
    {
        progressbar.setString(message);
        progressbar.setIndeterminate(true);
    }
    
    protected boolean askYesNoQuestion(String title, String question)
    {
        int result = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
    
    protected void showError(String title, String message)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public void stopProgressBar(String message)
    {
        progressbar.setIndeterminate(false);
        if(message != null)
        {
            progressbar.setString(message);
        }
    }
}
