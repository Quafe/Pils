package org.pilsgeschwader.furryironman.controller.evewho;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.evewho.EveWhoCorplist;

/**
 *
 * @author binary gamura
 */
public class EveWhoController 
{        
    private static final String baseAddress = "http://evewho.com/api.php";   
    
    public EveWhoCorplist getCorpList(int corpId) throws IOException, ControllerException, URISyntaxException
    {
        try(CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            URI uri = new URIBuilder(baseAddress).addParameter("type", "corplist").addParameter("id", String.valueOf(corpId)).build();            
            HttpGet getRequest = new HttpGet(uri);

            CloseableHttpResponse response = httpClient.execute(getRequest);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() != 200)
            {
                throw new ControllerException("request on \""+getRequest.getURI()+"\" failed. (response code "+statusLine.getStatusCode()+" => )"+statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            Gson gson = new GsonBuilder().create();
            EveWhoCorplist result = gson.fromJson(new InputStreamReader(entity.getContent()), EveWhoCorplist.class);
            return result;            
        }
    }
}
