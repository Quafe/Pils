package org.pilsgeschwader.furryironman.controller.skills;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseHandler;
import org.pilsgeschwader.furryironman.controller.common.XMLApiResponseReader;
import org.pilsgeschwader.furryironman.model.app.XMLElements;
import org.pilsgeschwader.furryironman.model.eve.EvEAugumentor;
import org.pilsgeschwader.furryironman.model.eve.EvESkillBonus;
import org.pilsgeschwader.furryironman.model.eve.EvESkillDefinition;
import org.pilsgeschwader.furryironman.model.eve.EvESkillTree;
import org.pilsgeschwader.furryironman.model.eve.EvESkillTreeGroup;
import org.pilsgeschwader.furryironman.model.eve.EveSkillTreeRequirement;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
 */
public class SkillTreeImporter implements XMLApiResponseHandler
{
    private static final Logger logger = Logger.getLogger(SkillTreeImporter.class.getName());
    
    private EvESkillTree skilltree;
    
    private EvESkillTreeGroup currentGroup;
    
    private EvESkillDefinition currentSkill;
    
    public EvESkillTree loadSkilltree(InputStream stream) throws ParserConfigurationException, SAXException, IOException
    {

        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        skilltree = new EvESkillTree();
        currentGroup = null;
        currentSkill = null;
        XMLApiResponseReader handler = new XMLApiResponseReader(this);
        parser.parse(stream, handler);
        skilltree.sortGroups();
        skilltree.createIndex();
        return skilltree;
    }
    

    @Override
    public void onUnknownElementStart(String element, Map<String, String> values)
    {
        
    }

    @Override
    public void onUnknownElementEnd(String element, String content)
    {
        switch(element)
        {
            case "rank":
                currentSkill.setRank(Integer.valueOf(content.trim()));
                break;
            case "description":
                currentSkill.setDescription(content);
                break;
            case "secondaryattribute":
                currentSkill.setSecondaryAttribute(EvEAugumentor.Type.valueOf(content.trim().toUpperCase()));
                break;                
            case "primaryattribute":
                currentSkill.setPrimaryAttribute(EvEAugumentor.Type.valueOf(content.trim().toUpperCase()));
                break;
        }
    }

    @Override
    public void onRowSet(String name, String key, String[] columns, Stack<String> rowsets)
    {
        
    }

    @Override
    public void onRow(Map<String, String> values, Stack<String> rowsets)
    {
        String topElement = rowsets.peek();
        if(topElement.equals(XMLElements.Rowsets.SKILLGROUPS))
        {
            String groupname = values.get(XMLElements.Attributes.GROUPNAME);
            int groupId = Integer.valueOf(values.get(XMLElements.Attributes.GROUPID));
            currentGroup = skilltree.createGroup(groupId, groupname);
        }
        else if(topElement.equals(XMLElements.Rowsets.SKILLS))
        {            
            currentSkill = new EvESkillDefinition();
            currentSkill.setTypeName(values.get(XMLElements.Attributes.TYPENAME));
            currentSkill.setGroupID(Integer.valueOf(values.get(XMLElements.Attributes.GROUPID)));
            currentSkill.setTypeID(Integer.valueOf(values.get(XMLElements.Attributes.TYPEID)));
            currentSkill.setPublished(values.get(XMLElements.Attributes.PUBLISHED).equals("1"));
            
            
            currentGroup.addSkill(currentSkill);
        }
        else if(topElement.equals(XMLElements.Rowsets.REQUIREDSKILLS))
        {
            int typeId = Integer.valueOf(values.get(XMLElements.Attributes.TYPEID));
            int skillLevel = Integer.valueOf(values.get(XMLElements.Attributes.SKILLLEVEL));
            
            
            EveSkillTreeRequirement requirement = new EveSkillTreeRequirement();
            requirement.setSkillLevel(skillLevel);
            requirement.setTypeID(typeId);
            currentSkill.getRequirements().add(requirement);
                    
        }
        else if(topElement.equals(XMLElements.Rowsets.SKILLBONUSCOLLECTION))
        {
            EvESkillBonus bonus = new EvESkillBonus();
            bonus.setType(EvESkillBonus.Type.fromString(values.get(XMLElements.Attributes.BONUSTYPE)));
            bonus.setValue(Float.valueOf(values.get(XMLElements.Attributes.BONUSVALUE)));
            currentSkill.getBonis().add(bonus);
        }
    }
}
