package org.pilsgeschwader.furryironman.controller.common;

import java.util.Map;
import java.util.Stack;

/**
 *
 * @author binarygamura
 */
public interface XMLApiResponseHandler
{
    public void onUnknownElementStart(String element, Map<String, String> values);
    
    public void onUnknownElementEnd(String element, String content);
    
    public void onRowSet(String name, String key, String[] columns, Stack<String> rowsets);
    
    public void onRow(Map<String, String> values, Stack<String> rowsets);
}
