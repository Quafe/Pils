package org.pilsgeschwader.furryironman.controller.apikey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.AbstractController;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.controller.common.XMLApiRequest;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.xml.sax.SAXException;

/**
 *
 * @author boreas
 */
public class ApiKeyController extends AbstractController
{
    private static final String DELIMITER = ";";
    
    private static final Logger logger = Logger.getLogger(ApiKeyController.class.getName());
    
    public ApiKeyController()
    {
        
    }
    
    public void validate(ApiKey key) throws IOException, ControllerException, ParserConfigurationException, SAXException, URISyntaxException
    {
        logger.log(Level.INFO, "validating key {0}", key);
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.API_KEY_INFO);
        request.setXmlHandler(new ApiKeyHandler(key));
        makeApiXMLRequest(request, key);
    }
    
    public void save(List<ApiKey> storedKeys, OutputStream stream) throws IOException
    {
        if(storedKeys != null)
        {
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream)))
            {
                for(ApiKey key : storedKeys)
                {
                    writer.write(String.valueOf(key.getKeyId()));
                    writer.write(DELIMITER);
                    writer.write(key.getVerificationString());
                    writer.write("\n");
                }
                logger.log(Level.INFO, "saved {0} api keys.", storedKeys.size());
                writer.flush();
            }
        }
    }
    
    public List<ApiKey> load(InputStream stream) throws IOException
    {
        List<ApiKey> storedKeys = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
        {
            String line;
            int counter = 0;
            while((line = reader.readLine()) != null)
            {
                storedKeys.add(loadLine(line));
                counter++;
            }
            logger.log(Level.INFO, "read {0} api keys.", counter);
        }
        return storedKeys;
    }
    
    public void validateAll(List<ApiKey> keys) throws IOException, ControllerException, ParserConfigurationException, SAXException, URISyntaxException
    {
        for(ApiKey key : keys)
        {
            validate(key);
        }
    }
    
    
    
    
    private ApiKey loadLine(String line)
    {
        String[] splitted = line.split(DELIMITER, 2);
        ApiKey key = new ApiKey();
        key.setKeyId(Integer.valueOf(splitted[0]));
        key.setVerificationString(splitted[1]);
        
        return key;
    }       
}
