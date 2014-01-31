package org.pilsgeschwader.combatmapper.view.character;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacter;
import org.pilsgeschwader.combatmapper.view.EvECombatMapper;
import org.pilsgeschwader.combatmapper.view.common.AbstractDialog;

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
