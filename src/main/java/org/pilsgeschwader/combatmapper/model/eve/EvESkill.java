package org.pilsgeschwader.combatmapper.model.eve;

/**
 *
 * @author boreas
 */
public class EvESkill
{
    private int typeId;
    
    private int skillpoints;
    
    private int level;
    
    private EvEItemDefinition itemDefinition;

    public int getTypeId()
    {
        return typeId;
    }

    public EvEItemDefinition getItemDefinition()
    {
        return itemDefinition;
    }

    public void setItemDefinition(EvEItemDefinition itemDefinition)
    {
        this.itemDefinition = itemDefinition;
    }
    
    public void setTypeId(int typeId)
    {
        this.typeId = typeId;
    }

    public int getSkillpoints()
    {
        return skillpoints;
    }

    public void setSkillpoints(int skillpoints)
    {
        this.skillpoints = skillpoints;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    @Override
    public String toString()
    {
        return "EvESkill{" + "typeId=" + typeId + ", skillpoints=" + skillpoints + ", level=" + level + ", itemDefinition=" + itemDefinition + '}';
    }
}
