package org.pilsgeschwader.furryironman.model.eve;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author binary gamura
 */
public class EvESkillDefinition 
{
    private String typeName;
    
    private int groupID;
    
    private int typeID;
    
    private boolean published;
    
    private String description;
    
    private int rank;
    
    private final List<EveSkillTreeRequirement> requirements;
    
    private EvEAugumentor.Type primaryAttribute;
    
    private EvEAugumentor.Type secondaryAttribute;
    
    private final List<EvESkillBonus> bonis;
    
    public EvESkillDefinition()
    {
        requirements = new ArrayList<>();
        bonis = new ArrayList<>();
    }

    public EvEAugumentor.Type getPrimaryAttribute()
    {
        return primaryAttribute;
    }

    public void setPrimaryAttribute(EvEAugumentor.Type primaryAttribute)
    {
        this.primaryAttribute = primaryAttribute;
    }

    public EvEAugumentor.Type getSecondaryAttribute()
    {
        return secondaryAttribute;
    }

    public void setSecondaryAttribute(EvEAugumentor.Type secondaryAttribute)
    {
        this.secondaryAttribute = secondaryAttribute;
    }

    public List<EveSkillTreeRequirement> getRequirements()
    {
        return requirements;
    }

    public List<EvESkillBonus> getBonis()
    {
        return bonis;
    }
    
    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public int getGroupID()
    {
        return groupID;
    }

    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }

    public int getTypeID()
    {
        return typeID;
    }

    public void setTypeID(int typeID)
    {
        this.typeID = typeID;
    }

    public boolean isPublished()
    {
        return published;
    }

    public void setPublished(boolean published)
    {
        this.published = published;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    @Override
    public String toString()
    {
        return "EvESkillDefinition{" + "typeName=" + typeName + ", groupID=" + groupID + ", typeID=" + typeID + ", published=" + published + ", description=" + description + ", rank=" + rank + ", requirements=" + requirements + ", primaryAttribute=" + primaryAttribute + ", secondaryAttribute=" + secondaryAttribute + ", bonis=" + bonis + '}';
    }
    
}
