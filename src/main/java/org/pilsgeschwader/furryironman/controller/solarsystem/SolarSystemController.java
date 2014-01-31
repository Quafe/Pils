package org.pilsgeschwader.furryironman.controller.solarsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.model.eve.SolarSystem;

/**
 *
 * @author boreas
 */
public class SolarSystemController
{    
    public SolarSystemController()
    {
        
    }
    
    private static final Logger logger = Logger.getLogger(SolarSystemController.class.getName());
    
    public List<SolarSystem> readSystems(InputStream stream) throws IOException
    {
        String line;
        List<SolarSystem> systems = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
        {
            logger.info("starting to read solar systems from file.");
            while((line = reader.readLine()) != null)
            {
                systems.add(readSystem(line));
            }
            logger.log(Level.INFO, "read {0} solar systems from file.", systems.size());
        }
        return systems;
    }
    
    private SolarSystem readSystem(String line)
    {
        String[] splitted = line.split(";");
        SolarSystem system = new SolarSystem();
        system.setName(splitted[0]);
        system.setX(Float.valueOf(splitted[1]));
        system.setY(Float.valueOf(splitted[2]));
        system.setZ(Float.valueOf(splitted[3]));
        system.setRegionId(Integer.valueOf(splitted[4]));
        system.setConstellationId(Integer.valueOf(splitted[5]));
        system.setSolarSystemId(Integer.valueOf(splitted[6]));
        system.setSecurity(Float.valueOf(splitted[7]));
        return system;
    }
}
