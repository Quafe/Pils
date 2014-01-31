package org.pilsgeschwader.combatmapper.view.character;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacter;
import org.pilsgeschwader.combatmapper.view.EvECombatMapper;
import org.pilsgeschwader.combatmapper.view.common.AbstractDialog;

/**
 *
 * @author boreas
 */
public class CharacterInfo extends AbstractDialog
{
    public CharacterInfo(EvECharacter character, EvECombatMapper parent)
    {
        super(character.getCharacterName(), parent);
        init();
    }
    
    private void init()
    {
        JTabbedPane pane = new JTabbedPane();
        
        add(pane, BorderLayout.CENTER);
        
        
    }
}
