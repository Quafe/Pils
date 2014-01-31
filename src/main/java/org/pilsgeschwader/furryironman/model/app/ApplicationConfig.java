package org.pilsgeschwader.furryironman.model.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import org.pilsgeschwader.furryironman.controller.common.PropertyNotFoundException;

/**
 *
 * @author binarygamura
 */
public class ApplicationConfig
{
    public static final String DATABASE_PASSWORD = "db_password";
    public static final String DATABASE_USER = "db_user";
    public static final String DATABASE_PORT = "db_port";
    public static final String DATABASE_HOST = "db_host";
    public static final String DATABASE_NAME = "db_name";
    
    private final HashMap<String, String> properties;
    
    private File file;
    
    public ApplicationConfig()
    {
        properties = new HashMap<>();
    }
    
    public void save() throws FileNotFoundException, IOException
    {
        save(file);
    }
    
    public void save(File file) throws FileNotFoundException, IOException
    {
        this.file = file;
        Properties props = new Properties();
        for(String key : properties.keySet())
        {
            props.setProperty(key, properties.get(key));
        }
        props.store(new FileOutputStream(file), "");
    }
    
    public int getPropertyAsInt(String key) throws PropertyNotFoundException
    {
        return Integer.valueOf(getPropertyAsString(key));
    }
    
    public String getPropertyAsString(String key) throws PropertyNotFoundException
    {
        if(!properties.containsKey(key))
        {
            throw new PropertyNotFoundException(key);
        }
        return properties.get(key);
    }
    
    public void setProperty(String key, Object value)
    {
        properties.put(key, String.valueOf(value));
    }
    
//    public void setProperty(String key, String value)
//    {
//        properties.put(key, value);
//    }
    
    public ApplicationConfig load(File file) throws IOException
    {
        String temp;
        this.file = file;
        properties.clear();
        Properties props = new Properties();
        props.load(new FileInputStream(file));
        for(Object key : props.keySet())
        {
            temp = key.toString();
            properties.put(temp, props.getProperty(temp));
        }
        return this;
    }
    
}
