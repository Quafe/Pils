package org.pilsgeschwader.furryironman.controller.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.model.eve.EvEItemDefinition;

/**
 *
 * @author boreas
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while((line = reader.readLine()) != null)
        {
            loadItem(line, definitions);
        }
        logger.log(Level.INFO, "done reading {0} item definitions.", definitions.size());
        return definitions;
    }
    
    /*
    EvEItemDefinition definition = new EvEItemDefinition();
        0 definition.setBasePrice(result.getFloat("basePrice"));
        1 definition.setCapacity(result.getFloat("capacity"));
        2 definition.setPortionSize(result.getInt("portionSize"));
        3 definition.setGroupID(result.getInt("groupID"));
        4 definition.setTypeName(result.getString("typeName"));
        5 definition.setDescription(result.getString("description"));
        6 definition.setRaceId(result.getInt("raceID"));
        7 definition.setPublished(result.getInt("published") == 1);
        8 definition.setMarketGroupID(result.getInt("marketGroupID"));
        9 definition.setChanceOfDuplicating(result.getDouble("chanceOfDuplicating"));
        10 definition.setMass(result.getDouble("mass"));
        11 definition.setVolume(result.getDouble("volume"));
        12 definition.setTypeID(result.getInt("typeID"));
    
        0 builder.append(String.valueOf(definition.getBasePrice())).append(";");
        1 builder.append(String.valueOf(definition.getCapacity())).append(";");
        2 builder.append(String.valueOf(definition.getChanceOfDuplicating())).append(";");
        3 builder.append(String.valueOf(definition.getDescription())).append(";");
        4 builder.append(String.valueOf(definition.getGroupID())).append(";");
        5 builder.append(String.valueOf(definition.getMarketGroupID())).append(";");
        6 builder.append(String.valueOf(definition.getMass())).append(";");
        7 builder.append(String.valueOf(definition.getPortionSize())).append(";");
        8 builder.append(String.valueOf(definition.getRaceId())).append(";");
        9 builder.append(String.valueOf(definition.getTypeID())).append(";");
        10 builder.append(String.valueOf(definition.getTypeName())).append(";");
        11 builder.append(String.valueOf(definition.getVolume())).append("\n");
    
    */
    private void loadItem(String line, Map<Integer, EvEItemDefinition> definitions)
    {
        line = line.trim();
        if(!line.isEmpty())
        {
            String[] splitted = line.split(";");
            EvEItemDefinition definition = new EvEItemDefinition();
            definition.setBasePrice(Float.valueOf(splitted[0]));
            definition.setCapacity(Double.valueOf(splitted[1]));
            definition.setChanceOfDuplicating(Double.valueOf(splitted[2]));
            definition.setDescription(splitted[3]);
            definition.setGroupID(Integer.valueOf(splitted[4]));
            definition.setMarketGroupID(Integer.valueOf(splitted[5]));
            definition.setMass(Double.valueOf(splitted[6]));            
            definition.setPortionSize(Integer.valueOf(splitted[7]));            
            definition.setRaceId(Integer.valueOf(splitted[8]));                        
            definition.setTypeID(Integer.valueOf(splitted[9]));
            definition.setTypeName(splitted[10]);
            definition.setVolume(Double.valueOf(splitted[11]));
            definition.setPublished(Boolean.valueOf(splitted[12]));            
            
            
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
