package org.pilsgeschwader.furryironman.model.eve;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author binary gamura
 */
public class EvESkillTreeGroup implements Iterable<EvESkillDefinition>
{
    private int groupID;
    
    private String groupname;

    private final List<EvESkillDefinition> skills;
    
    public EvESkillTreeGroup()
    {
        skills = new ArrayList<>();
    }

    public List<EvESkillDefinition> getSkills()
    {
        return skills;
    }
    
    public void addSkill(EvESkillDefinition skill)
    {
        skills.add(skill);
    }
    
    public int getGroupID()
    {
        return groupID;
    }

    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }

    
    public void sortSkills()
    {
        Collections.sort(skills, new SkillDefinitionComparator());
    }
    
    public String getGroupname()
    {
        return groupname;
    }

    public void setGroupname(String groupname)
    {
        this.groupname = groupname;
    }

    @Override
    public Iterator<EvESkillDefinition> iterator()
    {
        return skills.iterator();
    }
    
    private static class SkillDefinitionComparator implements Comparator<EvESkillDefinition>
    {

        private final Collator collator;
        
        private SkillDefinitionComparator()
        {
            collator = Collator.getInstance();
        }
        
        @Override
        public int compare(EvESkillDefinition o1, EvESkillDefinition o2)
        {
            return collator.compare(o1.getTypeName(), o2.getTypeName());
        }
        
    }
}
