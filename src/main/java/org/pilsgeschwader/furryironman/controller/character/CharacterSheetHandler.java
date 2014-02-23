package org.pilsgeschwader.furryironman.controller.character;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pilsgeschwader.furryironman.controller.common.Controller;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.model.eve.EvEAugumentor;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;
import org.pilsgeschwader.furryironman.model.eve.EvESkill;

/**
 *
 * @author binarygamura
 */
public class CharacterSheetHandler implements XMLApiResponseHandler
{
    private final EvECharacterSheet sheet;
    
    private State state;
    
    private enum State {CONTENT, IN_SKILLS, IN_CERT, IN_CORP_ROLES, IN_CORP_HQ_ROLES, IN_CORP_TITLES, IN_ACCOUNTS};
    
    private final Controller controller;
    
    private static final Logger logger = Logger.getLogger(CharacterSheetHandler.class.getName());
    
    private final DateFormat format;
    
    private String lastAugmentorName;
    
    private String lastAugmentorType;
    
    public CharacterSheetHandler(Controller controller)
    {
        sheet = new EvECharacterSheet();
        this.controller = controller;
        state = State.CONTENT;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Override
    public void onCachedUntil(Date date)
    {
        
    }
    
    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        switch(element)
        {
            case "memorybonus":
                lastAugmentorType = element;
                break;
            case "willpowerbonus":
                lastAugmentorType = element;
                break;
            case "perceptionbonus":
                lastAugmentorType = element;
                break;
            case "intelligencebonus":
                lastAugmentorType = element;
                break;
            case "charismabonus":
                lastAugmentorType = element;
                break;
        }
    }

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        switch(element)
        {
            case "augmentatorname":
                lastAugmentorName = content;
                break;
            case "augmentatorvalue":
                EvEAugumentor augomentor = new EvEAugumentor();
                augomentor.setImplantValue(Integer.valueOf(content));
                augomentor.setImplantName(lastAugmentorName);
                if(lastAugmentorType != null)
                {
                    switch(lastAugmentorType)
                    {
                        case "memorybonus":
                            augomentor.setType(EvEAugumentor.Type.MEMORY);
                            break;
                        case "charismabonus":
                            augomentor.setType(EvEAugumentor.Type.CHARISMA);
                            break;
                        case "intelligencebonus":
                            augomentor.setType(EvEAugumentor.Type.INTELLIGENCE);
                            break;
                        case "perceptionbonus":
                            augomentor.setType(EvEAugumentor.Type.PERCEPTION);
                            break;
                        case "willpowerbonus":
                            augomentor.setType(EvEAugumentor.Type.WILLPOWER);
                            break;
                        default:
                            logger.log(Level.WARNING, "unable to handle augumentor type \"{0}\".", lastAugmentorType);
                            break;
                    }
                    sheet.addAugumentor(augomentor);
                }
                else
                {
                    logger.log(Level.WARNING, "no augmentor type set for {0}!", lastAugmentorName);
                }
                break;
            case "intelligence":
                sheet.setIntelligence(Integer.valueOf(content));
                break;
            case "memory":
                sheet.setMemory(Integer.valueOf(content));
                break;
            case "charisma":
                sheet.setCharisma(Integer.valueOf(content));
                break;
            case "perception":
                sheet.setPerception(Integer.valueOf(content));
                break;
            case "willpower":
                sheet.setWillpower(Integer.valueOf(content));
                break;
            case "corporationname":
                sheet.setCorporationName(content);
                break;
            case "corporationid":
                sheet.setCorporationID(Integer.valueOf(content));
                break;
            case "name":                
                sheet.setName(content);
                break;
            case "characterid":
                sheet.setCharacterID(Integer.valueOf(content));
                break;
            case "dob":
                try
                {
                    format.parse(content);
                }
                catch(ParseException ex){ex.printStackTrace(System.err);}
                break;
            case "race":
                sheet.setRace(content);
                break;
            case "bloodline":
                sheet.setBloodline(content);
                break;
            case "gender":
                sheet.setGender(content);
                break;
            case "clonename":
                sheet.setCloneName(content);
                break;
            case "cloneskillpoints":
                sheet.setCloneSkillPoints(Integer.valueOf(content));
                break;
            case "balance":
                sheet.setBalance(Float.valueOf(content));
                break;  
            case "ancestry":                
                sheet.setAncestry(content);
                break;
            default:
                logger.log(Level.WARNING, "unhandled element: \"{0}\".", element);
                break;
        }
    }

    @Override
    public void onRowSet(String name, String key, String[] columns, Stack<String> rowsets)
    {
        switch(key)
        {
            case "accounts":
                state = State.IN_ACCOUNTS;
                break;
            case "skills":                
                state = State.IN_SKILLS;
                break;
            case "certificates":
                state = State.IN_CERT;
                break;
            case "corporationroles":
                state = State.IN_CORP_ROLES;
                break;
            case "corporationrolesathq":
                state = State.IN_CORP_HQ_ROLES;
                break;
            case "corporationtitles":
                state = State.IN_CORP_TITLES;
                break;
            case "corporationrolesatbase":
                //TODO write me!!!
                break;
            case "corporationrolesatother":
                //TODO write me!!!
                break;
            default:
                logger.log(Level.WARNING, "rowset \"{0}\" is unknown!", key);
        }
    }

    @Override
    public void onRow(Map<String, String> values, Stack<String> rowsets)
    {
        switch(state)
        {
            case IN_ACCOUNTS:
                sheet.setBalance(Float.valueOf(values.get("balance")));
                break;
            case IN_CERT:
                break;
            case IN_CORP_HQ_ROLES:
                sheet.addCorpHqRole(values.get("rolename"));
                break;
            case IN_CORP_ROLES:
                sheet.addCorpRole(values.get("rolename"));
                break;
            case IN_CORP_TITLES:
                sheet.addCorpTitle(values.get("titlename"));
                break;
            case IN_SKILLS:
                EvESkill skill = new EvESkill();
                skill.setTypeId(Integer.valueOf(values.get("typeid")));
                skill.setLevel(Integer.valueOf(values.get("level")));
                skill.setSkillpoints(Integer.valueOf(values.get("skillpoints")));
                skill.setItemDefinition(controller.getModel().getItemDefinitionById(skill.getTypeId()));
                sheet.addSkill(skill);
                break;                
            default:
                logger.warning("in row within illegal state!");
        }
    }    

    public EvECharacterSheet getSheet()
    {
        return sheet;
    }
}
