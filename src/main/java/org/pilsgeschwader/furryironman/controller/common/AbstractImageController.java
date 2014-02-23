package org.pilsgeschwader.furryironman.controller.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author binarygamura
 */
public class AbstractImageController //extends AbstractController
{
    private final File imageBaseDir;
    
    private final Map<String, Image> imageCache;
    
    private static final Logger logger = Logger.getLogger(AbstractImageController.class.getName());
    
    private final String expectedImageType;
    
    public static final String IMAGE_TYPE_JPEG = "jpg";
    
    public static final String IMAGE_TYPE_PNG = "png";
    
    private final URI baseUri;
    
    public AbstractImageController(File imageBaseDir, URI baseUri, String expectedImageType)
    {
        this.imageBaseDir = imageBaseDir;
        this.expectedImageType = expectedImageType;
        this.baseUri = baseUri;
        imageCache = new HashMap<>();
    }
    
    private BufferedImage requestImage(int id, int size) throws IOException, ControllerException
    {
        try(CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            String key = id+"_"+size;
            HttpPost postRequest = new HttpPost(baseUri+"/"+key+"."+expectedImageType);

            CloseableHttpResponse response = httpClient.execute(postRequest);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() != 200)
            {
                throw new ControllerException("request on \""+postRequest.getURI()+"\" failed. (response code "+statusLine.getStatusCode()+" => )"+statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            BufferedImage image = ImageIO.read(entity.getContent());
            EntityUtils.consume(entity);
            return image;
        }
    }
    
    public Image fetchImageFor(int id, int size) throws IOException, ControllerException
    {
        return fetchImageFor(id, size, false);
    }
    
    public Image fetchImageFor(int id, int size, boolean force) throws IOException, ControllerException
    {
        BufferedImage image;
        String key = id+"_"+size;
        if(!imageCache.containsKey(key))
        {
            String targetPath = imageBaseDir.getAbsolutePath()+"/"+key+"."+expectedImageType;
            File targetFile = new File(targetPath);
            if(targetFile.exists() && !force)
            {
                try(FileInputStream fileStream = new FileInputStream(targetFile))
                {
                    image = ImageIO.read(fileStream);
                    imageCache.put(key, image);
                }
            }
            else
            {
                image = requestImage(id, size);
                if(image != null)
                {
                    targetFile.createNewFile();
                    ImageIO.write(image, expectedImageType, targetFile);
                    imageCache.put(key, image);
                }
                else
                {
                    logger.log(Level.WARNING, "unable to fetch image for id {0}.", id);
                }
            }
        }
        return imageCache.get(key);        
    }    
}
