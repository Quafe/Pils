package org.pilsgeschwader.furryironman.controller.trade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.model.eve.EveWalletJournalEntry;

/**
 *
 * @author binary gamura
 */
public class JournalWalletHandler implements XMLApiResponseHandler
{

    private Logger logger = Logger.getLogger(JournalWalletHandler.class.getName());
    
    private DateFormat dateFormat;

    private List<EveWalletJournalEntry> entries;
    
    public JournalWalletHandler()
    {
        entries = new ArrayList<>(0);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public List<EveWalletJournalEntry> getEntries()
    {
        return entries;
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
        EveWalletJournalEntry entry = new EveWalletJournalEntry();
        
        try
        {
            Date date = dateFormat.parse(values.get("date"));
            entry.setRefID(Long.valueOf(values.get("refid")));
            entry.setRefTypeID(Integer.valueOf(values.get("reftypeid")));
            entry.setOwnerName1(values.get("ownername1"));
            entry.setOwnerId1(Integer.valueOf(values.get("ownerid1")));
            entry.setOwnerName2(values.get("ownername2"));
            entry.setOwnerId2(Integer.valueOf(values.get("ownerid2")));
            /*Varies*/
            entry.setArgName1(values.get("argname1"));
            entry.setArgId1(Integer.valueOf(values.get("argid1")));
            entry.setAmount(Float.valueOf(values.get("amount")));
            entry.setBalance(Float.valueOf(values.get("balance")));
            entry.setReason(values.get("reason"));
            entry.setTaxRecieverId(values.get("taxrecieverid"));
            entry.setTaxAmount(values.get("taxamount"));  
            entry.setDate(date);
      
        }
        
        catch(ParseException ex)
        {
            logger.warning("error while parsing date string... bitch!");
        }
        
        
        entries.add(entry);
    }

    @Override
    public void onCachedUntil(Date date)
    {
        
    }

}
