package org.pilsgeschwader.combatmapper.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pilsgeschwader.furryironman.controller.apikey.ApiKeyController;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.pilsgeschwader.furryironman.controller.common.XMLApiRequest;

/**
 *
 * @author boreas
 */
public class KeyTest
{
    private static ApiKeyController controller;
    
    private static DateFormat format;
    
    @BeforeClass
    public static void initTest()
    {
         controller = new ApiKeyController();
         format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    @Ignore
    public void testApiKeyPermissions()
    {
        ApiKey key = new ApiKey();
        key.setAccessMask(268435455);
        List<XMLApiRequest.Target> matching = XMLApiRequest.Target.getMatching(key);
        
        assertTrue(matching.contains(XMLApiRequest.Target.ACCOUNT_STATUS));
    }
    
    
    @Test(expected = ControllerException.class)
    public void testParseInvalidApiKeyInfo() throws IOException, ControllerException, ParserConfigurationException, SAXException, URISyntaxException
    {
        long start = System.currentTimeMillis();
        ApiKey validKey = new ApiKey(2656713, "j8webTIcKK8WD6gJOqw3EIah57HNBUEVCg5HbcEvzdKwW8uc4JaUz8bUHhsrFibRdd");        
        controller.validate(validKey);
   
        assertNotNull(validKey.getValidTo());
        String dateString = format.format(validKey.getValidTo());
        assertTrue(validKey.getAccessMask() == 268435455);
        assertTrue(dateString+" is unexpected!", dateString.equals("2014-10-06 15:47:25"));
        
        System.out.println("got key data within "+(System.currentTimeMillis() - start)+"ms.");
    }
    
    @Test
    @Ignore
    public void testParseValidApiKeyInfo() throws IOException, ControllerException, ParserConfigurationException, SAXException, URISyntaxException
    {
        long start = System.currentTimeMillis();
        ApiKey validKey = new ApiKey(2656713, "j8webTIcKK8WD6gJOqw3EIah57HNBUEVCg5HbcEvzdKwW8uc4JaUz8bUHhsrFibR");        
        controller.validate(validKey);
   
        assertNotNull(validKey.getValidTo());
        String dateString = format.format(validKey.getValidTo());
        assertTrue(validKey.getAccessMask() == 268435455);
        assertTrue(dateString+" is unexpected!", dateString.equals("2014-10-06 15:47:25"));
        
        System.out.println("got key data within "+(System.currentTimeMillis() - start)+"ms.");
        
    }
}
