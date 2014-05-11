package org.pilsgeschwader.furryironman.controller.skills;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.controller.common.Util;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.model.eve.EvESkillInTrainingInfo;

/**
 *
 * @author binary gamura
 */
public class SkillInTrainingHandler implements XMLApiResponseHandler
{
    private static final Logger logger = Logger.getLogger(SkillInTrainingHandler.class.getName());
    
    private final EvESkillInTrainingInfo result;

    private final DateFormat dateformat;
    
    public SkillInTrainingHandler()
    {
        result = new EvESkillInTrainingInfo();
        dateformat = Util.createDefaultEVEFormat();
//        dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        
    }

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        try
        {
            if(content == null)
            {
                content = "";
            }
            switch(element)
            {
                case "skillintraining":
                    result.setSkillInTraining(Integer.parseInt(content.isEmpty() ? "0" : content));
                    break;
                case "trainingtolevel":
                    result.setTrainingToLevel(Integer.parseInt(content));
                    break;
                case "trainingdestinationsp":
                    result.setTrainingDestinationSP(Integer.parseInt(content));
                    break;
                case "trainingstartsp":
                    result.setTrainingStartSP(Integer.parseInt(content));
                    break;
                case "trainingtypeid":
                    result.setTrainingTypeID(Integer.parseInt(content));
                    break;
                case "trainingstarttime":
                    result.setTrainingStartTime(dateformat.parse(content));
                    break;
                case "trainingendtime":
                    result.setTrainingEndTime(dateformat.parse(content));
                    break;
                case "currenttqtime":
                    result.setCurrentTqTime(dateformat.parse(content));
                    break;

            }
        }
        catch(ParseException ex)
        {
            logger.log(Level.WARNING, "unable to parse date from \"{0}\".", content);
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
    public void onCachedUntil(Date date)
    {
        
    }

    public EvESkillInTrainingInfo getResult()
    {
        return result;
    }
}
