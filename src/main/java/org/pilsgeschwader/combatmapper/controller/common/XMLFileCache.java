package org.pilsgeschwader.combatmapper.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author boreas
 */
public class XMLFileCache
{
    private final File basedir;
    
    public XMLFileCache(File basedir)
    {
        this.basedir = basedir;
    }
    
    public boolean containsElementForId(int id)
    {
        return getElementFile(id).exists();
    }
    
    public void putElementFor(int id, String xml)
    {
        
    }
    
    private File getElementFile(int id)
    {
        File cacheFile = new File(basedir, id+".xml");
        return cacheFile;
    }
            
    public InputStream getElementStreamForId(int id) throws FileNotFoundException
    {
        InputStream stream = null;
        File cacheFile = getElementFile(id);
        if(cacheFile != null && cacheFile.exists())
        {
            stream = new FileInputStream(cacheFile);
        }
        return stream;
    }
    
}
