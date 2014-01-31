package org.pilsgeschwader.furryironman.view.icons;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author binarygamura
 */
public class IconCache
{
    private static final Map<String, Image> cache = new HashMap<>();
    
    public static ImageIcon getIcon(String name)
    {
        if(!cache.containsKey(name))
        {
            try
            {
                Image image = ImageIO.read(IconCache.class.getResourceAsStream(name));
                cache.put(name, image);
            }
            catch(IOException ex){}
        }
        return new ImageIcon(cache.get(name));
    }    
}
