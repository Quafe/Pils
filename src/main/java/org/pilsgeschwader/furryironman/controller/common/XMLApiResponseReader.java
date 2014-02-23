package org.pilsgeschwader.furryironman.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.pilsgeschwader.furryironman.model.app.XMLElements;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author binarygamura
 */
public class XMLApiResponseReader extends DefaultHandler
{
    private final XMLApiResponseHandler handler;
        
    private final List<String> ignorableTags = new ArrayList<String>(){{add(XMLElements.EXPIRES);add(XMLElements.CURRENTTIME);add(XMLElements.RESULT);add(XMLElements.EVEAPI);}};
    
    private StringBuilder textBuffer;
    
    private static final Logger logger = Logger.getLogger(XMLApiResponseReader.class.getName());
    
    private final Stack<String> rowsets;
    
    private final DateFormat dateFormat;
    
    private Date cachedUntil;
    
    public XMLApiResponseReader(XMLApiResponseHandler handler)
    {
        this.handler = handler;
        rowsets = new Stack<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public Date getCachedUntil()
    {
        return cachedUntil;
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
        try
        {
            qName = qName.toLowerCase();
            if(qName.equals("cacheduntil"))
            {
                cachedUntil = dateFormat.parse(textBuffer.toString().trim());
                handler.onCachedUntil(cachedUntil);
            }
            else if(qName.equals(XMLElements.ROWSET))
            {
                rowsets.pop();
            }
            else if(!ignorableTags.contains(qName) && !qName.equals(XMLElements.ROW) && !qName.equals(XMLElements.ROWSET))
            {
                handler.onUnknownElementEnd(qName, textBuffer.toString());

            }
            if(textBuffer.length() > 0)
            {
                textBuffer = new StringBuilder();
            }
        }
        catch(ParseException ex)
        {
            logger.log(Level.SEVERE, "error while parsing cached until element from \"{0}\".", textBuffer.toString());
            ex.printStackTrace(System.err);
        }
    }
    
    private Map<String, String> convertAttributes(Attributes attributes)
    {
        String qName;
        Map<String, String> data = new HashMap<>();
        for(int i = 0; i < attributes.getLength(); i++)
        {
            qName = attributes.getQName(i);
            data.put(qName.toLowerCase(), attributes.getValue(qName));
        }
        return data;
    }

    private String[] extractColumnNames(Attributes attributes)
    {
        String[] columnNames = new String[0];
        String value = attributes.getValue(XMLElements.COLUMNS);
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
                    String name = attributes.getValue(XMLElements.NAME).toLowerCase();
                    handler.onRowSet(qName, name, extractColumnNames(attributes), rowsets);
                    rowsets.push(name);
                    break;
                case "row":
                    handler.onRow(convertAttributes(attributes), rowsets);
                    break;
                default:
                    handler.onUnknownElementStart(qName, convertAttributes(attributes));
                    break;
            }
        }
    }
}
