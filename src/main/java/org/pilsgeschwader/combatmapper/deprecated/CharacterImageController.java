//package org.pilsgeschwader.combatmapper.deprecated;
//
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import org.apache.http.HttpEntity;
//import org.apache.http.StatusLine;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.pilsgeschwader.combatmapper.controller.common.AbstractController;
//import org.pilsgeschwader.combatmapper.controller.common.ControllerException;
//import org.pilsgeschwader.combatmapper.model.eve.EvECharacter;
//
//
///**
// *
// * @author boreas
// */
//public class CharacterImageController extends AbstractController
//{    
//    private final File imageBaseDir;
//    
//    private final Map<Integer, Image> characterCache;
//    
//    private static final Logger logger = Logger.getLogger(CharacterImageController.class.getName());
//    
//    public CharacterImageController(File imageBaseDir)
//    {
//        this.imageBaseDir = imageBaseDir;
//        characterCache = new HashMap<>();
//    }
//    
//    private BufferedImage requestImage(String url, int id, int size, String imagetype) throws IOException, ControllerException
//    {
//        try(CloseableHttpClient httpClient = HttpClients.createDefault())
//        {
//            HttpPost postRequest = new HttpPost(url+"/"+id+"_"+size+"."+imagetype);
//
//            CloseableHttpResponse response = httpClient.execute(postRequest);
//            StatusLine statusLine = response.getStatusLine();
//            if(statusLine.getStatusCode() != 200)
//            {
//                throw new ControllerException("request on \""+postRequest.getURI()+"\" failed. (response code "+statusLine.getStatusCode()+" => )"+statusLine.getReasonPhrase());
//            }
//            HttpEntity entity = response.getEntity();
//            BufferedImage image = ImageIO.read(entity.getContent());
//            EntityUtils.consume(entity);
//            return image;
//        }
//    }
//    
//    public Image fetchImageFor(EvECharacter character, int size) throws IOException, ControllerException
//    {
//        BufferedImage image;
//        if(character != null)
//        {
//            int characterId = character.getCharacterID();
//            if(!characterCache.containsKey(characterId))
//            {
//                String targetPath = imageBaseDir.getAbsolutePath()+"/character/"+characterId+"_"+size+".jpg";
//                File targetFile = new File(targetPath);
//                if(targetFile.exists())
//                {
//                    try(FileInputStream fileStream = new FileInputStream(targetFile))
//                    {
//                        image = ImageIO.read(fileStream);
//                        characterCache.put(characterId, image);
//                    }
//                }
//                else
//                {
//                    image = requestImage("http://image.eveonline.com/Character/", characterId, 64, "jpg");
//                    if(image != null)
//                    {
//                        targetFile.createNewFile();
//                        ImageIO.write(image, "jpg", targetFile);
//                        characterCache.put(characterId, image);
//                    }
//                    else
//                    {
//                        logger.log(Level.WARNING, "unable to fetch image for character {0}.", character.getCharacterName());
//                    }
//
//                }
//            }
//            return characterCache.get(characterId);
//        }
//        return null;
//        
//    }
//}
