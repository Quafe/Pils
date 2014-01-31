package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.view.EvECombatMapper;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;

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
