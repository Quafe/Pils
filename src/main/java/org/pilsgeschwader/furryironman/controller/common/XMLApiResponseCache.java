package org.pilsgeschwader.furryironman.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class XMLApiResponseCache
{
    private final File basedir;

    private int cacheHits;

    private int cacheUses;

    private final DateFormat dateFormat;

    private static final Logger logger = Logger.getLogger(XMLApiResponseCache.class.getName());

    public XMLApiResponseCache(File basedir)
    {
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        this.basedir = basedir;
    }

    private Date getExpireDate(File file) throws ParseException
    {
        Date expireDate = null;
        String[] nameParts = file.getName().split("_");
        if(nameParts.length > 0)
        {
            String dateString = nameParts[nameParts.length - 1];
            expireDate = dateFormat.parse(dateString.substring(0, dateString.length() - 4));
        }
        return expireDate;
    }

    public void makeApiXMLRequest(XMLApiRequest request, ApiKey apiKey) throws ParseException, FileNotFoundException, IOException, ControllerException, ParserConfigurationException, SAXException
    {
        cacheUses++;
        HttpEntity entity = null;
        File tempFile = null;
        InputStream stream = null;
        
        if(apiKey != null)
        {
            Util.buildDefaultParameters(apiKey.getVerificationString(), apiKey.getKeyId(), request.getArguments());
            request.setKey(apiKey);
        }
        
        //does an cache entry exist?
        String cacheKey = request.createCacheKey();
        String[] matches = basedir.list(new XMLApiResponseCacheFileFilter(cacheKey));
        assert matches.length == 1;
        //do we have an match?
        if(matches.length > 0)
        {
            //is it still valid?
            File cacheFile = new File(basedir, matches[0]);
            Date cachedUntil = getExpireDate(cacheFile);
            //cache entry too old?
            if(cachedUntil.before(new Date()))
            {
                //we can delete it...
                cacheFile.delete();
                SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                logger.log(Level.INFO, "cache hit, but file is to outdated. ({0} > {1}).", new Object[]{df.format(cachedUntil), df.format(new Date())});
            }
            else //valid cache entry!! YAY!!
            {
                stream = new FileInputStream(cacheFile);
                logger.info("great!! xml cache hit!!");
                cacheHits++;
            }
        }
        if(stream == null) //no match... sad panda..
        {
            //we need to get the contents...
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
                    throw new InvalidApiKeyException(null); //TODO: do something smarter than "null". 
                }
                else if(statusLine.getStatusCode() != 200)
                {
                    throw new ControllerException("request on \""+postRequest.getURI()+"\" failed. (response code "+statusLine.getStatusCode()+" => )"+statusLine.getReasonPhrase());
                }
                entity = response.getEntity();
                //.. and put it into temporary cache file.
                //we dont know the cached until date yet, so it has to be renamed later.
                stream = entity.getContent();
                byte[] buffer = new byte[1024];
                int bytesRead;
                //since we dont know the cached until date, which will later
                //be part of the file name, we have to store the xml response
                //to an temporary file.
                tempFile = File.createTempFile("response_", ".xml", basedir);
                //copy (^-^)
                try(FileOutputStream tempStream = new FileOutputStream(tempFile))
                {
                    while((bytesRead = stream.read(buffer, 0, buffer.length)) != -1)
                    {
                        tempStream.write(buffer, 0, bytesRead);
                    }
                    tempStream.flush();
                }
                //we prepare the stream from the newly created cache file.
                stream = new FileInputStream(tempFile);
            }            
        }
        //time to handle the response. 
        XMLApiResponseReader reader = new XMLApiResponseReader(request.getXmlHandler());
        reader.read(stream);
        stream.close();

        //after parsing the xml we know the cached until date and can finally
        //rename the temp file.
        if(tempFile != null)
        {
            String finalFileName = cacheKey+"_"+dateFormat.format(reader.getCachedUntil())+".xml";
            if(!tempFile.renameTo(new File(basedir, finalFileName)))
            {
                logger.log(Level.WARNING, "unable to remove {0} to {1}!.", new Object[]{tempFile.getAbsolutePath(), finalFileName});
            }
        }
    }

    public void makeApiXMLRequest(XMLApiRequest request, List<ApiKey> keys) throws ControllerException, IOException, ParserConfigurationException, SAXException, ParseException
    {
        ApiKey matchingKey = XMLApiRequest.Target.getMatchingKeyForMask(keys, request.getTarget());
        if(matchingKey == null)
        {
            throw new ControllerException("missing permission for \""+request.getTarget()+"\".");
        }
        makeApiXMLRequest(request, matchingKey);
    }
    
    static class XMLApiResponseCacheFileFilter implements FilenameFilter
    {
        private final String key;

        public XMLApiResponseCacheFileFilter(String key)
        {
            this.key = key;
        }

        @Override
        public boolean accept(File dir, String name)
        {
            return name.startsWith(key);
        }

    }
}
