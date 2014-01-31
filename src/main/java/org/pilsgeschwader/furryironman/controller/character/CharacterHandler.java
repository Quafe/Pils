package org.pilsgeschwader.furryironman.controller.character;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;

/**
 *
 * @author binarygamura
 */
public class CharacterHandler implements XMLApiResponseHandler
{
    private final List<EvECharacter> characters;
    
    private final List<Integer> knownCharacters;
    
    private static final Logger logger = Logger.getLogger(CharacterHandler.class.getName());
    
    private ApiKey apiKey;
    
    public CharacterHandler(ApiKey apiKey)
    {
        this.apiKey = apiKey;
        characters = new ArrayList<>();
        knownCharacters = new ArrayList<>();
    }

    
    
    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        
    }

    @Override
    public void onRowSet(String name, String key, String[] columns)
    {
        
    }

    public List<EvECharacter> getResult()
    {
        return characters;
    }
    
    /**
     * <row name="Mary" characterID="150267069" corporationName="Starbase Anchoring Corp" corporationID="150279367" />
     * @param values 
     */
    @Override
    public void onRow(Map<String, String> values)
    {
        
        Integer characterId = Integer.valueOf(values.get("characterID"));
        if(!knownCharacters.contains(characterId))
        {
            EvECharacter character = new EvECharacter();
            character.setCharacterID(characterId);
            character.setCharacterName(values.get("name"));
            character.setCorporationID(Integer.valueOf(values.get("corporationID")));
            character.setCorporationName(values.get("corporationName"));
            character.addKey(apiKey);
            characters.add(character);
            knownCharacters.add(characterId);
            logger.log(Level.INFO, "recived character \"{0}\" (id:{1})", new Object[]{character.getCharacterName(), (Integer) character.getCharacterID()});            
        }
    } 

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        
    }
}
