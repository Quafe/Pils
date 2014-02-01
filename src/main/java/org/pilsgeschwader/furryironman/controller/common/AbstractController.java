package org.pilsgeschwader.furryironman.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
 */
public class AbstractController
{
    protected void buildDefaultParameters(String vCode, int keyId, Map<String, String> map)
    {
        map.put("vCode", vCode);
        map.put("keyId", String.valueOf(keyId));
    }
    
    public void makeApiXMLRequest(XMLApiRequest request, ApiKey apiKey) throws ControllerException, IOException, ParserConfigurationException, SAXException
    {
        if(apiKey != null)
        {
            buildDefaultParameters(apiKey.getVerificationString(), apiKey.getKeyId(), request.getArguments());        
        }
        
        try(CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            HttpPost postRequest = new HttpPost(request.getTarget().getAddress());
            List<NameValuePair> parameter = new ArrayList<>(request.getArguments().size());
            for(String key : request.getArguments().keySet())
            {
                parameter.add(new BasicNameValuePair(key, request.getArguments().get(key)));
            }
            postRequest.setEntity(new UrlEncodedFormEntity(parameter));
            CloseableHttpResponse response = httpClient.execute(postRequest);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == 403)
            {
                throw new InvalidApiKeyException(apiKey);
            }
            else if(statusLine.getStatusCode() != 200)
            {
                throw new ControllerException("request on \""+postRequest.getURI()+"\" failed. (response code "+statusLine.getStatusCode()+" => )"+statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
//            System.err.println(EntityUtils.toString(entity));
            XMLApiResponseReader reader = new XMLApiResponseReader(request.getXmlHandler());
            reader.read(entity.getContent());
            EntityUtils.consume(entity);            
        }
    }
    
    public void makeApiXMLRequest(XMLApiRequest request, List<ApiKey> keys) throws ControllerException, IOException, ParserConfigurationException, SAXException
    {
        ApiKey matchingKey = XMLApiRequest.Target.getMatchingKeyForMask(keys, request.getTarget());
        if(matchingKey == null)
        {
            throw new ControllerException("missing permission for \""+request.getTarget()+"\".");
        }
        makeApiXMLRequest(request, matchingKey);
        
    }   
}
