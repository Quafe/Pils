package org.pilsgeschwader.combatmapper.controller.common;

import org.pilsgeschwader.combatmapper.controller.apikey.ApiKeyController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.combatmapper.controller.character.CharacterController;
import org.pilsgeschwader.combatmapper.controller.solarsystem.SolarSystemController;
import org.pilsgeschwader.combatmapper.model.app.ApplicationConfig;
import org.pilsgeschwader.combatmapper.model.app.Model;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacter;
import org.xml.sax.SAXException;

/**
 *
 * @author boreas
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
    
    public Controller() throws URISyntaxException
    {
        model = new Model();
        keyController = new ApiKeyController();
        solarSystemController = new SolarSystemController();
        characterController = new CharacterController();
        itemDefinitionController = new ItemDefinitionController();
        characterImageController = new AbstractImageController(new File("./images/character"), new URI("http://image.eveonline.com/Character/"), AbstractImageController.IMAGE_TYPE_JPEG);        
        corpImageController = new AbstractImageController(new File("./images/corp"), new URI("http://image.eveonline.com/Corporation/"), AbstractImageController.IMAGE_TYPE_PNG);
    }

    public Model getModel()
    {
        return model;
    }

    public void init(ApplicationConfig config) throws IOException, ClassNotFoundException, SQLException, PropertyNotFoundException, URISyntaxException, ParserConfigurationException, SAXException, ControllerException
    {
        this.config = config;
        
        model.setStoredKeys(keyController.load(new FileInputStream(KEY_STORE_FILE)));
        model.setSolarSystems(solarSystemController.readSystems(new FileInputStream(SOLAR_SYSTEMS_FILE)));
        model.setCharacters(characterController.loadAllCharacters(model.getStoredKeys()));        
        model.setItemDefinitions(itemDefinitionController.loadItems(new FileInputStream(ITEM_DEF_FILE)));
    }
    
    public void revalidateAllApiKeys() throws IOException, ControllerException, ParserConfigurationException, SAXException, URISyntaxException
    {
        keyController.validateAll(model.getStoredKeys());
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
    }
    
    public void reloadCharacterList() throws URISyntaxException, IOException, ParserConfigurationException, SAXException, ControllerException
    {
        model.setCharacters(characterController.loadAllCharacters(model.getStoredKeys()));
    }
    
    public void shutDown()
    {        
        try
        {
            keyController.save(model.getStoredKeys(), new FileOutputStream(KEY_STORE_FILE));
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
