package org.pilsgeschwader.furryironman.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.pilsgeschwader.furryironman.model.eve.SolarSystem;

/**
 *
 * @author boreas
 */
public class SystemGenerator
{    
    private Connection connection;
    
    public void connect(String host, String database, String user, String password) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection( "jdbc:mysql://"+host+":3306/"+database, user, password );
    }
    
    public List<SolarSystem> fetchSolarSystems() throws SQLException
    {
        List<SolarSystem> solarSystems = new ArrayList<>();
        try(Statement statement = connection.createStatement())
        {
            String query = "SELECT * FROM mapsolarsystems ORDER BY solarSystemName";
            ResultSet result = statement.executeQuery(query);
            while(result.next())
            {
                fetchSolarSystem(solarSystems, result);
            }
        }
        return solarSystems;
    }
    
    public void disconnect()
    {
        try
        {
            if(connection != null)
            {
                connection.close();
            }
        }
        catch(SQLException ex){}
        finally
        {
            connection = null;
        }
    }
    
    
    public void writeSolarSystemsToFile(File outputFile, List<SolarSystem> solarSystems) throws FileNotFoundException, IOException
    {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile))))
        {
            for(SolarSystem system : solarSystems)
            {
                writer.write(formatSolarSystem(system));
            }
            writer.flush();
        }
    }
    
    private String formatSolarSystem(SolarSystem system)
    {
        StringBuilder builder = new StringBuilder(system.getName()).append(";");
        builder.append(system.getX()).append(";");
        builder.append(system.getY()).append(";");
        builder.append(system.getZ()).append(";");
        builder.append(system.getRegionId()).append(";");
        builder.append(system.getConstellationId()).append(";");
        builder.append(system.getSolarSystemId()).append(";");
        builder.append(system.getSecurity());
                
        return builder.append("\n").toString();
    }
    
    private void fetchSolarSystem(List<SolarSystem> solarSystems, ResultSet result) throws SQLException
    {
        SolarSystem system = new SolarSystem();
        system.setX(result.getFloat("x"));
        system.setY(result.getFloat("y"));
        system.setZ(result.getFloat("z"));
        system.setName(result.getString("solarSystemName"));
        system.setRegionId(result.getInt("regionID"));
        system.setConstellationId(result.getInt("constellationID"));
        system.setSolarSystemId(result.getInt("solarSystemID"));
        system.setSecurity(result.getFloat("security"));
        
        solarSystems.add(system);
    }
    
    public static void main(String[] args)
    {
        try
        {
            
            long start = System.currentTimeMillis();
            
            String password = "";
            String user = "root";
            String host = "localhost";
            String database = "eve_data";
            File output = new File("./solar_systems.csv");

            
            
            SystemGenerator generator = new SystemGenerator();
            generator.connect(host, database, user, password);
            List<SolarSystem> fetchSolarSystems = generator.fetchSolarSystems();
            System.out.println("loaded "+fetchSolarSystems.size()+" system from database.");
            generator.writeSolarSystemsToFile(output, fetchSolarSystems);
            
            System.out.println("written systems to \""+output.getAbsolutePath()+"\".");
            System.out.println("done after "+(System.currentTimeMillis() - start)+"ms.");
        }
        catch(IOException | ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace(System.err);
        } 
    }
}
