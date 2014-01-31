package org.pilsgeschwader.combatmapper.controller.character;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.combatmapper.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacterStatus;

/**
 *
 * @author boreas
 */
public class CharacterStatusHandler implements XMLApiResponseHandler
{
    private final SimpleDateFormat format;
    
    private final EvECharacterStatus status;
    
    private static final Logger logger = Logger.getLogger(CharacterStatusHandler.class.getName());
    
    public CharacterStatusHandler()
    {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        status = new EvECharacterStatus();
    }
    
    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        
    }

    @Override
    public void onRowSet(String name, String key, String[] columns)
    {
        
    }

    public EvECharacterStatus getStatus()
    {
        return status;
    }
    

    @Override
    public void onRow(Map<String, String> values)
    {
        
    }

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        try
        {
            switch(element)
            {
                case "paiduntil":
                    status.setPaidUntil(format.parse(content));
                    break;
                case "createdate":
                    status.setCreateDate(format.parse(content));
                    break;
                case "logoncount":
                    status.setLogonCount(Integer.valueOf(content));
                    break;
                case "logonminutes":
                    status.setLoginMinutes(Integer.valueOf(content));
                    break;
            }
        }
        catch(ParseException | NumberFormatException ex)
        {
            logger.log(Level.WARNING, "unable to parse result from \"{0}\".", content);
            ex.printStackTrace(System.err);
        }
    }    
}
