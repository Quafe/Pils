package org.pilsgeschwader.furryironman.model.evewho;

/**
 *
 * @author binary gamura
 */
public class EveWhoCorplist 
{
    private EveWhoCorpInfo info;
    
    private EveWhoCharacter[] characters;

    public EveWhoCorpInfo getInfo()
    {
        return info;
    }

    public void setInfo(EveWhoCorpInfo info)
    {
        this.info = info;
    }

    public EveWhoCharacter[] getCharacters()
    {
        return characters;
    }

    public void setCharacters(EveWhoCharacter[] characters)
    {
        this.characters = characters;
    }
}
