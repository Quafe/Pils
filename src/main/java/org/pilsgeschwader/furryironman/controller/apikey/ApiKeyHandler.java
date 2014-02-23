package org.pilsgeschwader.furryironman.controller.apikey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.model.app.XMLElements;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;

class ApiKeyHandler implements XMLApiResponseHandler
{
    private final ApiKey key;

    private final DateFormat dateFormat;
    
    private static final Logger logger = Logger.getLogger(ApiKeyHandler.class.getName());

    ApiKeyHandler(ApiKey key)
    {
        this.key = key;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        try
        {
            if(element.equals(XMLElements.KEY))
            {
                String dateValue = values.get(XMLElements.EXPIRES);
                key.setAccessMask(Integer.parseInt(values.get(XMLElements.ACCESSMASK)));
                key.setValidTo(dateFormat.parse(dateValue == null || dateValue.isEmpty() ? "2100-01-01 00:00:00" : dateValue));
            }
        }
        catch(NumberFormatException | ParseException ex)
        {
            logger.severe(ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void onRowSet(String name, String key, String[] columns, Stack<String> rowsets)
    {

    }

    @Override
    public void onRow(Map<String, String> values, Stack<String> rowsets)
    {

    }

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        
    }

    @Override
    public void onCachedUntil(Date date)
    {
        
    }
}