package org.pilsgeschwader.combatmapper.view.character;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author boreas
 */
public abstract class CharacterSheetDialogApp extends JPanel implements Runnable
{
    protected String title = "unknown";

    protected CharacterSheetDialog parent;        
    
    public CharacterSheetDialogApp(CharacterSheetDialog parent)
    {
        this.parent = parent;
    }
    
    public String getTitle()
    {
        return title;
    }    
    
    public abstract void onReload();
    
    @Override
    public void run()
    {
        parent.startProgressBar(title);
        try
        {
            onReload();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(System.err);
        }
        finally
        {
            parent.stopProgressBar(null);
        }
    }
}
