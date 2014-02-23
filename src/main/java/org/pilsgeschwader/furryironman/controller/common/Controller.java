package org.pilsgeschwader.furryironman.controller.common;

import org.pilsgeschwader.furryironman.controller.apikey.ApiKeyController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.character.CharacterController;
import org.pilsgeschwader.furryironman.controller.skills.SkillTreeImporter;
import org.pilsgeschwader.furryironman.controller.solarsystem.SolarSystemController;
import org.pilsgeschwader.furryironman.model.app.ApplicationConfig;
import org.pilsgeschwader.furryironman.model.app.Model;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
 */
public class Controller
{    
    public final ApiKeyController keyController;
    
    public final SolarSystemController solarSystemController;
    
    public final CharacterController characterController;
    
    public final AbstractImageController characterImageController;
    
    public final AbstractImageController corpImageController;
    
    public final ItemDefinitionController itemDefinitionController;
    
    private final Model model;
    
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    
    private ApplicationConfig config;
    
    private static final String SOLAR_SYSTEMS_FILE = "./solar_systems.csv";
    
    private static final String KEY_STORE_FILE = "./api_keys.csv";
    
    private static final String ITEM_DEF_FILE = "./items.csv";
    
    private static final String XML_CACHE_DIR =  "./xml_cache/";
    
    public Controller() throws URISyntaxException
    {
        model = new Model();
        File xmlCacheDir = new File(XML_CACHE_DIR);
        keyController = new ApiKeyController(xmlCacheDir);
        solarSystemController = new SolarSystemController();
        characterController = new CharacterController(xmlCacheDir);
        itemDefinitionController = new ItemDefinitionController();
        characterImageController = new AbstractImageController(new File("./images/character"), new URI("http://image.eveonline.com/Character/"), AbstractImageController.IMAGE_TYPE_JPEG);        
        corpImageController = new AbstractImageController(new File("./images/corp"), new URI("http://image.eveonline.com/Corporation/"), AbstractImageController.IMAGE_TYPE_PNG);
    }

    public Model getModel()
    {
        return model;
    }

    public void init(ApplicationConfig config) throws IOException, ClassNotFoundException, SQLException, PropertyNotFoundException, URISyntaxException, ParserConfigurationException, SAXException, ControllerException, ParseException
    {
        this.config = config;
        
        try(InputStream stream = new FileInputStream(KEY_STORE_FILE))
        {
            model.setStoredKeys(keyController.load(stream));
            logger.info("done loading api keys.");
        }
        try(InputStream stream = new FileInputStream(SOLAR_SYSTEMS_FILE))
        {
            model.setSolarSystems(solarSystemController.readSystems(stream));
            logger.info("done loading solar systems.");
        }
        model.setCharacters(characterController.loadAllCharacters(model.getStoredKeys()));        
        logger.info("done loading characters.");
        try(InputStream stream = new FileInputStream(ITEM_DEF_FILE))
        {
            model.setItemDefinitions(itemDefinitionController.loadItems(stream));
            logger.info("done loading utems.");
        }
        SkillTreeImporter importer = new SkillTreeImporter();
        try(InputStream stream = new FileInputStream("./skills_2014_02_10.xml"))
        {
            model.setSkilltree(importer.loadSkilltree(stream));
        }
        logger.info("done loading skilltree.");
    }
    
    public void revalidateAllApiKeys() throws IOException, ControllerException, ParserConfigurationException, SAXException, URISyntaxException, ParseException
    {
        keyController.validateAll(model.getStoredKeys());
    }
            
    public EvECharacterSheet loadCharacterSheet(EvECharacter character) throws ControllerException, IOException, ParserConfigurationException, SAXException, ParseException
    {
        return characterController.loadCharacterSheet(character, this);
    }
    
    public void reloadAllEveCorporationImages()
    {
        for(EvECharacter character : model.getCharacters())
        {
            try
            {
                corpImageController.fetchImageFor(character.getCorporationID(), 64);
            }
            catch(IOException | ControllerException ex)
            {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    
    public void reloadAllCharacterImages()
    {
        for(EvECharacter character : model.getCharacters())
        {
            try
            {
                characterImageController.fetchImageFor(character.getCharacterID(), 64);
            }
            catch(IOException | ControllerException ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        logger.info("done reloading all character images..");
    }
    
    public void reloadCharacterList() throws URISyntaxException, IOException, ParserConfigurationException, SAXException, ControllerException, ParseException
    {
        model.setCharacters(characterController.loadAllCharacters(model.getStoredKeys()));
    }
    
    public void shutDown()
    {        
        try(OutputStream stream = new FileOutputStream(KEY_STORE_FILE))
        {
            keyController.save(model.getStoredKeys(), stream);
        }
        catch(IOException ex){}
        try
        {
            config.save();
        }
        catch(IOException ex)
        {
            logger.severe("error while saving configuration!");
            ex.printStackTrace(System.err);
        }
    }
}
