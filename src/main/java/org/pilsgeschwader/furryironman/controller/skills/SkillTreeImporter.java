package org.pilsgeschwader.furryironman.controller.skills;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseReader;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
 */
public class SkillTreeImporter implements XMLApiResponseHandler
{
    public void loadSkilltree(InputStream stream) throws ParserConfigurationException, SAXException, IOException
    {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        XMLApiResponseReader handler = new XMLApiResponseReader(this);
        parser.parse(stream, handler);
    }

    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        
    }

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        
    }

    @Override
    public void onRowSet(String name, String key, String[] columns, Stack<String> rowsets)
    {
        
    }

    @Override
    public void onRow(Map<String, String> values, Stack<String> rowsets)
    {
        
    }
}
