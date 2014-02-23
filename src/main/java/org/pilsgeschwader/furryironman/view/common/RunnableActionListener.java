package org.pilsgeschwader.furryironman.view.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author binarygamura
 */
public class RunnableActionListener implements ActionListener
{
    private final Runnable runnable;
    
    private boolean useEDT;
    
    public RunnableActionListener(Runnable runnable)
    {
        this(runnable, false);
    }
    
    public RunnableActionListener(Runnable runnable, boolean useEDT)
    {
        this.runnable = runnable;
        this.useEDT = useEDT;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(useEDT)
        {
            SwingUtilities.invokeLater(runnable);
        }
        else
        {
            new Thread(runnable).start();
        }
    }
}
