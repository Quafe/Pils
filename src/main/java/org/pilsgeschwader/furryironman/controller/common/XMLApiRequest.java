package org.pilsgeschwader.furryironman.controller.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;

/**
 *
 * @author binarygamura
 */
public class XMLApiRequest
{    
    private final Target target;
    
    private Map<String, String> arguments;
    
    private ApiKey key;
    
    private XMLApiResponseHandler xmlHandler;

    public XMLApiRequest(Target target)
    {
        this.target = target;
        arguments = new HashMap<>();
    }
    
    public String createCacheKey()
    {
        StringBuilder key = new StringBuilder(target.name()).append("_");
        List<String> argmuentKeys = new ArrayList<>(arguments.keySet());
        Collections.sort(argmuentKeys);
        for(String argumentKey : argmuentKeys)
        {
            key.append(argumentKey).append("_").append(arguments.get(argumentKey)).append("_");
        }
        if(this.key != null)
        {
            key.append(this.key.getKeyId());
        }
        return key.toString();
    }
    
    public XMLApiResponseHandler getXmlHandler()
    {
        return xmlHandler;
    }

    public void setXmlHandler(XMLApiResponseHandler xmlHandler)
    {
        this.xmlHandler = xmlHandler;
    }
    
    public Target getTarget()
    {
        return target;
    }

    public Map<String, String> getArguments()
    {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments)
    {
        this.arguments = arguments;
    }

    public ApiKey getKey()
    {
        return key;
    }

    public void setKey(ApiKey key)
    {
        this.key = key;
    }
    
    public enum Target
    {        
        SKILL_IN_TRAINING("https://api.eveonline.com//char/SkillInTraining.xml.aspx", 131072, "skill in training", true),
        
        ACCOUNT_STATUS("https://api.eveonline.com/account/AccountStatus.xml.aspx", 33554432, "account status", true),
    
        API_KEY_INFO("https://api.eveonline.com/account/APIKeyInfo.xml.aspx", 0, "api key info", false),

        LIST_OF_CHARACTERS("https://api.eveonline.com/account/Characters.xml.aspx", 0, "list of all characters", true),

        ACCOUNT_BALANCE("https://api.eveonline.com/char/AccountBalance.xml.aspx", 1, "account balance", true),

        ASSET_LIST("https://api.eveonline.com/char/AssetList.xml.aspx", 2, "asset list", true),

        KILL_LOG ("https://api.eveonline.com/char/Killlog.xml.aspx", 256, "all kill mails", true),
        
        CHARACTER_SHEET ("https://api.eveonline.com/char/CharacterSheet.xml.aspx", 8, "character sheet", true);
        
        private URI address;
        
        private final int accessMask;
        
        private final String description;
        
        private final boolean requiresApiKey;
        
        private Target(String address, int accessMask, String description, boolean requiresApiKey)
        {
            try
            {
                this.address = new URI(address);
            }
            catch(URISyntaxException ex)
            {
                ex.printStackTrace(System.err);
            }
            this.requiresApiKey = requiresApiKey;
            this.description = description;
            this.accessMask = accessMask;
        }

        public String getDescription()
        {
            return description;
        }
        

        public URI getAddress()
        {
            return address;
        }

        public int getAccessMask()
        {
            return accessMask;
        }
        
        
        public boolean matches(int accessMask)
        {
            return (accessMask & this.accessMask) == this.accessMask;
        }
        
        public static ApiKey getMatchingKeyForMask(List<ApiKey> keys, Target target)
        {
            ApiKey match = null;
            for(ApiKey key : keys)
            {
                if(target.matches(key.getAccessMask()))
                {
                    match = key;
                    break;
                }
            }
            return match;
        }

        public static List<Target> getMatching(ApiKey key)
        {
            List<Target> result = new ArrayList<>();
            int mask = key.getAccessMask();
            for(Target temp : values())
            {
                if(temp.matches(mask))
                {
                    result.add(temp);
                }
            }
            return result;
        }
    }
    
    
}
