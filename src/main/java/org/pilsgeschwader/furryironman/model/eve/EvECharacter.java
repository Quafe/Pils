package org.pilsgeschwader.furryironman.model.eve;

import java.util.ArrayList;
import java.util.List;

/**
 * * <row name="Mary" characterID="150267069" corporationName="Starbase Anchoring Corp" corporationID="150279367" />
 * @author binarygamura
 */
public class EvECharacter
{
    private EvESkillInTrainingInfo info;
    
    private int characterID;
    
    private String characterName;
    
    private int corporationID;
    
    private String corporationName;
    
    private final List<ApiKey> keys;
    
    public EvECharacter()
    {
        keys = new ArrayList<>();
        info = new EvESkillInTrainingInfo();
    }

    public EvESkillInTrainingInfo getInfo()
    {
        return info;
    }

    public void setInfo(EvESkillInTrainingInfo info)
    {
        this.info = info;
    }

    public List<ApiKey> getKeys()
    {
        return keys;
    }

    public void addKey(ApiKey key)
    {
        if(!keys.contains(key))
        {
            keys.add(key);
        }
    }

    public int getCharacterID()
    {
        return characterID;
    }

    public void setCharacterID(int characterID)
    {
        this.characterID = characterID;
    }

    public String getCharacterName()
    {
        return characterName;
    }

    public void setCharacterName(String characterName)
    {
        this.characterName = characterName;
    }

    public int getCorporationID()
    {
        return corporationID;
    }

    public void setCorporationID(int corporationID)
    {
        this.corporationID = corporationID;
    }

    public String getCorporationName()
    {
        return corporationName;
    }

    public void setCorporationName(String corporationName)
    {
        this.corporationName = corporationName;
    }
    
    
    
}
