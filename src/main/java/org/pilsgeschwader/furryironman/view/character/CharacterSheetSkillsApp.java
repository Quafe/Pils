package org.pilsgeschwader.furryironman.view.character;

/**
 *
 * @author boreas
 */
public class CharacterSheetSkillsApp extends CharacterSheetDialogApp
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
}