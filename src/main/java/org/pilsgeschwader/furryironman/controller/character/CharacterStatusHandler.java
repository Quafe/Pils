package org.pilsgeschwader.furryironman.controller.character;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterStatus;

/**
 *
 * @author binarygamura
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
    public void onRowSet(String name, String key, String[] columns, Stack<String> rowsets)
    {
        
    }

    public EvECharacterStatus getStatus()
    {
        return status;
    }
    

    @Override
    public void onRow(Map<String, String> values, Stack<String> rowsets)
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
                    status.setLogonCount(Integer.parseInt(content));
                    break;
                case "logonminutes":
                    status.setLoginMinutes(Integer.parseInt(content));
                    break;
            }
        }
        catch(ParseException | NumberFormatException ex)
        {
            logger.log(Level.WARNING, "unable to parse result from \"{0}\".", content);
            ex.printStackTrace(System.err);
        }
    }    

    @Override
    public void onCachedUntil(Date date)
    {
        
    }
}
