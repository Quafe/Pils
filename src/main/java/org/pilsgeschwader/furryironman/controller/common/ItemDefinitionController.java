package org.pilsgeschwader.furryironman.controller.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.model.eve.EvEItemDefinition;

/**
 *
 * @author binarygamura
 */
public class ItemDefinitionController
{
    private static final Logger logger = Logger.getLogger(ItemDefinitionController.class.getName());
    
    public ItemDefinitionController()
    {
        
    }
    
    public Map<Integer, EvEItemDefinition> loadItems(InputStream stream) throws IOException
    {
        String line;
        logger.info("start reading item definitions.");
        Map<Integer, EvEItemDefinition> definitions = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Util.createDefaultFileCharset())))
        {
            while((line = reader.readLine()) != null)
            {
                loadItem(line, definitions);
            }
        }
        logger.log(Level.INFO, "done reading {0} item definitions.", definitions.size());
        return definitions;
    }
    
   
    private void loadItem(String line, Map<Integer, EvEItemDefinition> definitions)
    {
        line = line.trim();
        if(!line.isEmpty())
        {
            String[] splitted = line.split(";");
            EvEItemDefinition definition = new EvEItemDefinition();
            definition.setBasePrice(Float.parseFloat(splitted[0]));
            definition.setCapacity(Double.parseDouble(splitted[1]));
            definition.setChanceOfDuplicating(Double.parseDouble(splitted[2]));
            definition.setDescription(splitted[3]);
            definition.setGroupID(Integer.parseInt(splitted[4]));
            definition.setMarketGroupID(Integer.parseInt(splitted[5]));
            definition.setMass(Double.parseDouble(splitted[6]));            
            definition.setPortionSize(Integer.parseInt(splitted[7]));            
            definition.setRaceId(Integer.parseInt(splitted[8]));                        
            definition.setTypeID(Integer.parseInt(splitted[9]));
            definition.setTypeName(splitted[10]);
            definition.setVolume(Double.parseDouble(splitted[11]));
            definition.setPublished(Boolean.parseBoolean(splitted[12]));            
            
            
            if(!definitions.containsKey(definition.getTypeID()))
            {
                definitions.put(definition.getTypeID(), definition);
            }
            else
            {
                logger.log(Level.WARNING, "item \"{0}\" already stored!", definition.getTypeID());
            }
            
        }
    }
}
