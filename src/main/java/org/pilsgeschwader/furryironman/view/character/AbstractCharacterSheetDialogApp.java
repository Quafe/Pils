package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;

/**
 *
 * @author binarygamura
 */
abstract class AbstractCharacterSheetDialogApp extends JPanel implements Runnable
{
    private static final String DEFAULT_TITLE = "unknown";
    
    protected String title = DEFAULT_TITLE;

    protected CharacterSheetDialog parent;        
    
    AbstractCharacterSheetDialogApp(CharacterSheetDialog parent)
    {
        super(new BorderLayout());
        this.parent = parent;
        
    }
    
    public String getTitle()
    {
        return title;
    }    
    
    public abstract void onData(EvECharacterSheet sheet);
    
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
