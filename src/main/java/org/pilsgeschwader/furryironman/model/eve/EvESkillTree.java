package org.pilsgeschwader.furryironman.model.eve;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author binary gamura
 */
public class EvESkillTree implements Iterable<EvESkillTreeGroup>
{
    private final List<EvESkillTreeGroup> groups;
    
    
    private final Map<Integer, EvESkillDefinition> idToSkill;
    
    public EvESkillTree()
    {
        groups = new ArrayList<>();
        idToSkill = new HashMap<>();
    }
    
    public EvESkillTreeGroup createGroup(int id, String name)
    {
        EvESkillTreeGroup group = getGroupById(id);
        if(group == null)
        {
            group = new EvESkillTreeGroup();
            group.setGroupID(id);
            group.setGroupname(name);
            groups.add(group);
        }
        return group;
    }

    public Map<Integer, EvESkillDefinition> getIdToSkill()
    {
        return idToSkill;
    }
    
    public void createIndex()
    {
        idToSkill.clear();
        for(EvESkillTreeGroup group : groups)
        {
            for(EvESkillDefinition skill : group)
            {
                idToSkill.put(skill.getTypeID(), skill);
            }
        }
    }
    
    public void sortGroups()
    {        
        Collections.sort(groups, new GroupComparator());
        for(EvESkillTreeGroup group : groups)
        {
            group.sortSkills();
        }
    }
    
    public EvESkillTreeGroup getGroupById(int id)
    {
        EvESkillTreeGroup result = null;
        for(EvESkillTreeGroup group : groups)
        {
            if(group.getGroupID() == id)
            {
                result = group;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<EvESkillTreeGroup> iterator()
    {
        return groups.iterator();
    }

    private static class GroupComparator implements Comparator<EvESkillTreeGroup>
    {
        private final Collator collator;
        
        public GroupComparator()
        {
            collator = Collator.getInstance();
        }

        @Override
        public int compare(EvESkillTreeGroup o1, EvESkillTreeGroup o2)
        {
            return collator.compare(o1.getGroupname(), o2.getGroupname());
        }
    }

    @Override
    public String toString()
    {
        return "EvESkillTree{" + "groups=" + groups + '}';
    }
}
