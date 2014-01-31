package org.pilsgeschwader.combatmapper.model.app;

import java.util.ArrayList;
import java.util.HashMap;
import org.pilsgeschwader.combatmapper.model.eve.SolarSystem;
import java.util.List;
import java.util.Map;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacter;
import org.pilsgeschwader.combatmapper.model.eve.ApiKey;
import org.pilsgeschwader.combatmapper.model.eve.EvEItemDefinition;

/**
 *
 * @author boreas
 */
public class Model
{
    private List<SolarSystem> solarSystems;
    
    private List<ApiKey> storedKeys;
    
    private List<EvECharacter> characters;
    
    private Map<Integer, EvEItemDefinition> itemDefinitions;
    
    public Model()
    {
        storedKeys = new ArrayList<>();
        solarSystems = new ArrayList<>();
        characters = new ArrayList<>();
        itemDefinitions = new HashMap<>();
    }

    public EvEItemDefinition getItemDefinitionById(int id)
    {
        return itemDefinitions.get(id);
    }
    
    public Map<Integer, EvEItemDefinition> getItemDefinitions()
    {
        return itemDefinitions;
    }

    public void setCharacters(List<EvECharacter> characters)
    {
        this.characters = characters;
    }

    public List<EvECharacter> getCharacters()
    {
        return characters;
    }

    
    public List<ApiKey> getStoredKeys()
    {
        return storedKeys;
    }

    public void setStoredKeys(List<ApiKey> storedKeys)
    {
        this.storedKeys = storedKeys;
    }
    
    public List<SolarSystem> getSolarSystems()
    {
        return solarSystems;
    }

    public void setSolarSystems(List<SolarSystem> solarSystems)
    {
        this.solarSystems = solarSystems;
    }

    public void setItemDefinitions(Map<Integer, EvEItemDefinition> itemDefinitions)
    {
        this.itemDefinitions = itemDefinitions;
    }
    
    
}
