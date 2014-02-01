package org.pilsgeschwader.furryironman.view.character;

import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;

/**
 *
 * @author binarygamura
 */
public class CharacterSheetSkillsApp extends AbstractCharacterSheetDialogApp
{
    public CharacterSheetSkillsApp(CharacterSheetDialog parent)
    {
        super(parent);
        title = "skills";
    }

    @Override
    public void onReload()
    {
        System.out.println("!YAY!");
    }

    @Override
    public void onData(EvECharacterSheet sheet)
    {
        
    }
}
