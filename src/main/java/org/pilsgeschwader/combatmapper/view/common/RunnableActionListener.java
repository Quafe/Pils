package org.pilsgeschwader.combatmapper.view.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author boreas
 */
public class RunnableActionListener implements ActionListener
{
    private final Runnable runnable;
    
    public RunnableActionListener(Runnable runnable)
    {
        this.runnable = runnable;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
