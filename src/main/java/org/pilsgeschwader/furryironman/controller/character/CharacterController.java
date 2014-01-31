package org.pilsgeschwader.furryironman.controller.character;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.AbstractController;
import org.pilsgeschwader.furryironman.controller.common.Controller;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.controller.common.XMLApiRequest;
import org.pilsgeschwader.furryironman.controller.common.XMLFileCache;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterStatus;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;
import org.xml.sax.SAXException;

/**
 *
 * @author boreas
 */
public class CharacterController extends AbstractController
{    
    private static final Logger logger = Logger.getLogger(CharacterController.class.getName());
    
    private final XMLFileCache charactersheetCache;
    
    public CharacterController()
    {
        charactersheetCache = new XMLFileCache(new File("./cache/charactersheets/"));
    }
    
    public EvECharacterStatus getCharacterStatus(EvECharacter character) throws URISyntaxException, IOException, ParserConfigurationException, SAXException, ControllerException
    {
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.ACCOUNT_STATUS);
        request.getArguments().put("characterID", String.valueOf(character.getCharacterID()));        
        CharacterStatusHandler handler = new CharacterStatusHandler();
        request.setXmlHandler(handler);
        makeApiXMLRequest(request, character.getKeys());
        return handler.getStatus();
    }
    
    public EvECharacterSheet loadCharacterSheet(EvECharacter character, Controller controller) throws ControllerException, IOException, ParserConfigurationException, SAXException
    {
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.CHARACTER_SHEET);
        CharacterSheetHandler handler = new CharacterSheetHandler(controller);
        request.setXmlHandler(handler);
        makeApiXMLRequest(request, character.getKeys());
        return handler.getSheet();
    }
    
    public List<EvECharacter> loadAllCharacters(List<ApiKey> keys) throws URISyntaxException, IOException, ParserConfigurationException, SAXException, ControllerException
    {
        List<EvECharacter> allCharacters = new ArrayList<>();
        long start = System.currentTimeMillis();
        CharacterHandler handler;
        Map<Integer, EvECharacter> tempResult = new HashMap<>();
        XMLApiRequest request;
        for(ApiKey key : keys)
        {
            if(XMLApiRequest.Target.LIST_OF_CHARACTERS.matches(key.getAccessMask()))
            {
                request = new XMLApiRequest(XMLApiRequest.Target.LIST_OF_CHARACTERS);
                handler = new CharacterHandler(key);
                request.setXmlHandler(handler);
                
                makeApiXMLRequest(request, key);
                logger.log(Level.INFO, "done reading characters from key {0}.", key.getKeyId());
                for(EvECharacter temp : handler.getResult())
                {
                    if(tempResult.containsKey(temp.getCharacterID()))
                    {
                        tempResult.get(temp.getCharacterID()).addKey(key);
                    }
                    else
                    {
                        tempResult.put(temp.getCharacterID(), temp);
                    }
                }            
            }
            else
            {
                logger.log(Level.WARNING, "api key {0} does not support reading the character list.", key.getKeyId());
            }
        }
        
        allCharacters.addAll(tempResult.values());
        logger.log(Level.INFO, "loaded {0} characters from eve api within {1}ms.", new Object[]{allCharacters.size(), (System.currentTimeMillis() - start)});
        return allCharacters;
    }
}
