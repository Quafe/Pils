/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.controller.trade;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.controller.common.XMLApiRequest;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseCache;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.model.eve.EveWalletJournalEntry;
import org.xml.sax.SAXException;



/**
 *
 * @author binary gamura
 */
public class JournalController extends XMLApiResponseCache
{

    public static final int MARKET_ESCROW = 42;
    
    public static final int TRANSACTION_TAX = 54;
    
    public static final int MARKET_TRANSACTION = 2;
    
    
    
    public JournalController(File basedir)
    {
        super(basedir);
    }
    
    
    private void filter(List<EveWalletJournalEntry> temp)
    {
        Iterator<EveWalletJournalEntry> iterator = temp.iterator();
        EveWalletJournalEntry entry;
        
        while (iterator.hasNext())
        {   
            entry = iterator.next();
            
            if (entry.getRefTypeID() != MARKET_ESCROW && entry.getRefTypeID() != MARKET_TRANSACTION && entry.getRefTypeID() != TRANSACTION_TAX)
            {
                iterator.remove();
            }
        }        
    }
    
    public List<EveWalletJournalEntry> getWholeJournal(EvECharacter who) throws ControllerException, IOException, ParserConfigurationException, SAXException, ParseException
    {   
        List<EveWalletJournalEntry> temp = null;
        List<EveWalletJournalEntry> result = new ArrayList<>();
        long fromID = 0;
        JournalEntryComparator comparator = new JournalEntryComparator();
        // nach dem who ist die anzahl der datensaetzte... von eve begrenzt auf 2560
        while (!(temp = getJournal(who, 2560, fromID)).isEmpty())
        {            
            Collections.sort(temp, comparator);
            fromID = temp.get(temp.size() - 1).getRefID();
            filter(temp);    
            result.addAll(temp);
        }
        return result;
    }
    
    public List<EveWalletJournalEntry> getJournal(EvECharacter who, int rowCount, long fromID) throws ControllerException, IOException, ParserConfigurationException, SAXException, ParseException
    {
        
        XMLApiRequest request = new XMLApiRequest(XMLApiRequest.Target.WALLET_JOURNAL);
        if (fromID != 0) 
        {
            request.getArguments().put("fromID", String.valueOf(fromID));            
        }
        request.getArguments().put("rowCount", String.valueOf(rowCount));
        request.getArguments().put("characterID", String.valueOf(who.getCharacterID()));
        JournalWalletHandler handler = new JournalWalletHandler();
        request.setXmlHandler(handler);
        makeApiXMLRequest(request, who.getKeys());
        return handler.getEntries();        
    }    
    
    
    class JournalEntryComparator implements Comparator<EveWalletJournalEntry>
    {

        @Override
        public int compare(EveWalletJournalEntry o1, EveWalletJournalEntry o2)
        {
            long id1 = o1.getRefID();
            long id2 = o2.getRefID();
            if(id1 > id2)
            {
                return -1;
            }
            else if(id1 < id2)
            {
                return 1;
            }
            return 0;
        }        
    }
}
