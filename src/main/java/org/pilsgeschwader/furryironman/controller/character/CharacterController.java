package org.pilsgeschwader.furryironman.controller.character;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.Controller;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.controller.common.InvalidApiKeyException;
import org.pilsgeschwader.furryironman.controller.common.XMLApiRequest;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseCache;
import org.pilsgeschwader.furryironman.controller.skills.SkillInTrainingHandler;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterStatus;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;
import org.pilsgeschwader.furryironman.model.eve.EvESkillInTrainingInfo;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
 */
public class CharacterController extends XMLApiResponseCache
{    
    private static final Logger logger = Logger.getLogger(CharacterController.class.getName());
    
    //TODO: make something useful
//    private final XMLApiResponseCache charactersheetCache;
    
    public CharacterController(File baseDir)
    {
        super(baseDir);
//        charactersheetCache = new XMLApiResponseCache(new File("./cache/charactersheets/"));
    }
    
    public EvESkillInTrainingInfo getSkillInTrainingInfo(EvECharacter character) throws ControllerException, IOException, ParserConfigurationException, SAXException, ParseException
    {
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.SKILL_IN_TRAINING);
        SkillInTrainingHandler handler = new SkillInTrainingHandler();
        request.setXmlHandler(handler);
        request.getArguments().put("characterID", String.valueOf(character.getCharacterID()));
//        request.getArguments().put("characterIDcache", "60");
        makeApiXMLRequest(request, character.getKeys());
        return handler.getResult();
    }
    
    public EvECharacterStatus getCharacterStatus(EvECharacter character) throws URISyntaxException, IOException, ParserConfigurationException, SAXException, ControllerException, ParseException
    {
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.ACCOUNT_STATUS);
        request.getArguments().put("characterID", String.valueOf(character.getCharacterID()));        
        CharacterStatusHandler handler = new CharacterStatusHandler();
        request.setXmlHandler(handler);
        makeApiXMLRequest(request, character.getKeys());
        return handler.getStatus();
    }
    
    public EvECharacterSheet loadCharacterSheet(EvECharacter character, Controller controller) throws ControllerException, IOException, ParserConfigurationException, SAXException, ParseException
    {
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.CHARACTER_SHEET);
        CharacterSheetHandler handler = new CharacterSheetHandler(controller);
        request.setXmlHandler(handler);
        request.getArguments().put("characterID", String.valueOf(character.getCharacterID()));
        makeApiXMLRequest(request, character.getKeys());
        return handler.getSheet();
    }
    
    public List<EvECharacter> loadAllCharacters(List<ApiKey> keys) throws URISyntaxException, IOException, ParserConfigurationException, SAXException, ControllerException, ParseException
    {
        List<EvECharacter> allCharacters = new ArrayList<>();
        long start = System.currentTimeMillis();
        CharacterHandler handler;
        Map<Integer, EvECharacter> tempResult = new HashMap<>();
        XMLApiRequest request;
        ApiKey key;
        Iterator<ApiKey> iterator = keys.iterator();
        while(iterator.hasNext())
//        for(ApiKey key : keys)
        {
            key = iterator.next();
            try
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
            catch(InvalidApiKeyException ex)
            {
                logger.log(Level.SEVERE, "the api key {0} is invalid an will be removed.", ex.getKey().getKeyId());
                iterator.remove();
            }
        }
        
        allCharacters.addAll(tempResult.values());
        logger.log(Level.INFO, "loaded {0} characters from eve api within {1}ms.", new Object[]{allCharacters.size(), (System.currentTimeMillis() - start)});
        return allCharacters;
    }
}
