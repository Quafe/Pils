package org.pilsgeschwader.furryironman.controller.common;

import java.util.Map;

/**
 *
 * @author boreas
 */
public interface XMLApiResponseHandler
{
    public void onUnknownElementStart(String element, Map<String, String> values);
    
    public void onUnknownElementEnd(String element, String content);
    
    public void onRowSet(String name, String key, String[] columns);
    
    public void onRow(Map<String, String> values);
}
