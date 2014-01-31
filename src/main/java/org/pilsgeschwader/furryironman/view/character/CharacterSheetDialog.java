package org.pilsgeschwader.furryironman.view.character;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.view.EvECombatMapper;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;

/**
 *
 * @author boreas
 */
public class CharacterSheetDialog extends AbstractDialog
{
    private final EvECombatMapper parent;
    
    public CharacterSheetDialog(EvECombatMapper parent, EvECharacter character)
    {
        super("Character Sheet", parent);
        this.parent = parent;
        init();
    }
    
    private void init()
    {
        
        
        JTabbedPane contentPanel = new JTabbedPane();
    }
}
