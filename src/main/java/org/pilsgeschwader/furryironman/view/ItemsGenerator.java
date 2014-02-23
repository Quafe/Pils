package org.pilsgeschwader.furryironman.view;

import java.io.BufferedWriter;
import java.io.File;
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
import org.pilsgeschwader.furryironman.controller.common.Util;
import org.pilsgeschwader.furryironman.model.eve.EvEItemDefinition;

/**
 *
 * @author binarygamura
 */
public class ItemsGenerator
{
    
    private Connection connection;
    
    public void connect(String host, String database, String user, String password) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection( "jdbc:mysql://"+host+":3306/"+database, user, password );
    }
    
    private List<EvEItemDefinition> fetchItems() throws SQLException
    {
        List<EvEItemDefinition> definitions = new ArrayList<>();
        String query = "SELECT * FROM invtypes";
        try(Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery(query))
        {
            while(result.next())
            {
                fetchItem(definitions, result);
            }
        }
        return definitions;
    }
    
    
    private void fetchItem(List<EvEItemDefinition> definitions, ResultSet result) throws SQLException
    {
        EvEItemDefinition definition = new EvEItemDefinition();
        definition.setBasePrice(result.getFloat("basePrice"));
        definition.setCapacity(result.getFloat("capacity"));
        definition.setPortionSize(result.getInt("portionSize"));
        definition.setGroupID(result.getInt("groupID"));
        definition.setTypeName(result.getString("typeName"));
        definition.setDescription(result.getString("description"));
        definition.setRaceId(result.getInt("raceID"));
        definition.setPublished(result.getInt("published") == 1);
        definition.setMarketGroupID(result.getInt("marketGroupID"));
        definition.setChanceOfDuplicating(result.getDouble("chanceOfDuplicating"));
        definition.setMass(result.getDouble("mass"));
        definition.setVolume(result.getDouble("volume"));
        definition.setTypeID(result.getInt("typeID"));
        
        definitions.add(definition);
    }
    
    private void writeToFile(File file, List<EvEItemDefinition> definitions) throws IOException
    {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Util.createDefaultFileCharset())))
        {
            for(EvEItemDefinition item : definitions)
            {
                writer.write(formatItem(item));
            }
            writer.flush();
        }
    }
    
    private String formatItem(EvEItemDefinition definition)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(definition.getBasePrice())).append(";");
        builder.append(String.valueOf(definition.getCapacity())).append(";");
        builder.append(String.valueOf(definition.getChanceOfDuplicating())).append(";");
        builder.append(String.valueOf(definition.getDescription())).append(";");
        builder.append(String.valueOf(definition.getGroupID())).append(";");
        builder.append(String.valueOf(definition.getMarketGroupID())).append(";");
        builder.append(String.valueOf(definition.getMass())).append(";");
        builder.append(String.valueOf(definition.getPortionSize())).append(";");
        builder.append(String.valueOf(definition.getRaceId())).append(";");
        builder.append(String.valueOf(definition.getTypeID())).append(";");
        builder.append(String.valueOf(definition.getTypeName())).append(";");
        builder.append(String.valueOf(definition.getVolume())).append(";");
        builder.append(String.valueOf(definition.isPublished())).append("\n");
        
        return builder.toString();
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
            File output = new File("./items.csv");

            
            
            ItemsGenerator generator = new ItemsGenerator();
            generator.connect(host, database, user, password);
            List<EvEItemDefinition> definitions = generator.fetchItems();
            System.out.println("loaded "+definitions.size()+" items from database.");
            generator.writeToFile(output, definitions);
            
            System.out.println("written systems to \""+output.getAbsolutePath()+"\".");
            System.out.println("done after "+(System.currentTimeMillis() - start)+"ms.");
        }
        catch(IOException | ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace(System.err);
        } 
    }
}
