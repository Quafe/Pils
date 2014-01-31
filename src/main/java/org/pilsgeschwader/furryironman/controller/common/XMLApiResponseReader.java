package org.pilsgeschwader.furryironman.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author boreas
 */
public class XMLApiResponseReader extends DefaultHandler
{
    private final XMLApiResponseHandler handler;
        
    private final List<String> ignorableTags = new ArrayList<String>(){{add("cacheduntil");add("currenttime");add("result");add("eveapi");}};
    
    private StringBuilder textBuffer;
    
    private static final Logger logger = Logger.getLogger(XMLApiResponseReader.class.getName());
    
    public XMLApiResponseReader(XMLApiResponseHandler handler)
    {
        this.handler = handler;
    }
    
    public void read(InputStream stream) throws ParserConfigurationException, SAXException, IOException
    {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(stream, this, null);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        textBuffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        qName = qName.toLowerCase();
        if(!ignorableTags.contains(qName) && !qName.equals("row") && !qName.equals("rowset"))
        {
            handler.onUnknownElementEnd(qName, textBuffer.toString());
            
        }
        if(textBuffer.length() > 0)
        {
            textBuffer = new StringBuilder();
        }
    }
    
    private Map<String, String> convertAttributes(Attributes attributes)
    {
        String qName;
        Map<String, String> data = new HashMap<>();
        for(int i = 0; i < attributes.getLength(); i++)
        {
            qName = attributes.getQName(i);
            data.put(qName, attributes.getValue(qName));
        }
        return data;
    }

    private String[] extractColumnNames(Attributes attributes)
    {
        String[] columnNames = new String[0];
        String value = attributes.getValue("columns");
        if(value != null)
        {
            columnNames = value.trim().split(",");
        }
        return columnNames;
        
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        textBuffer = new StringBuilder();
        qName = qName.toLowerCase();
        if(!ignorableTags.contains(qName))
        {
            switch(qName)
            {
                case "rowset":
                    handler.onRowSet(qName, attributes.getValue("name"), extractColumnNames(attributes));
                    break;
                case "row":
                    handler.onRow(convertAttributes(attributes));
                    break;
                default:
                    handler.onUnknownElementStart(qName, convertAttributes(attributes));
                    break;
            }
        }
    }
}
