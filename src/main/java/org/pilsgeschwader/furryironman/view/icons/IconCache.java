package org.pilsgeschwader.furryironman.view.icons;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author binarygamura
 */
public class IconCache
{
    private static final Map<String, Image> cache = new HashMap<>();
    
    private static final Logger logger = Logger.getLogger(IconCache.class.getName());
    
    public static ImageIcon getIcon(String name)
    {
        if(!cache.containsKey(name))
        {
            try
            {
                Image image = ImageIO.read(IconCache.class.getResourceAsStream(name));
                cache.put(name, image);
            }
            catch(IOException ex)
            {
                logger.log(Level.WARNING, "unable to load image {0}.", name);
            }
        }
        return new ImageIcon(cache.get(name));
    }    
}
