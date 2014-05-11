package org.pilsgeschwader.furryironman.model.evewho;

/**
 *
 * @author binary gamura
 */
public class EveWhoCharacter 
{
    private int character_id;
    
    private int corporation_id;
    
    private int alliance_id;
    
    private String name;

    public int getCharacter_id()
    {
        return character_id;
    }

    public void setCharacter_id(int character_id)
    {
        this.character_id = character_id;
    }

    public int getCorporation_id()
    {
        return corporation_id;
    }

    public void setCorporation_id(int corporation_id)
    {
        this.corporation_id = corporation_id;
    }

    public int getAlliance_id()
    {
        return alliance_id;
    }

    public void setAlliance_id(int alliance_id)
    {
        this.alliance_id = alliance_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "EveWhoCharacter{" + "character_id=" + character_id + ", corporation_id=" + corporation_id + ", alliance_id=" + alliance_id + ", name=" + name + '}';
    }
    
}
