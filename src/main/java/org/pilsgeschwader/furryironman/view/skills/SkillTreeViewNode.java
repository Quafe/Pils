package org.pilsgeschwader.furryironman.view.skills;

import org.pilsgeschwader.furryironman.model.eve.EvESkillDefinition;

/**
 *
 * @author binary gamura
 */
public class SkillTreeViewNode 
{
    private final EvESkillDefinition skill;

    public SkillTreeViewNode(EvESkillDefinition skill)
    {
        this.skill = skill;
    }
    
    public EvESkillDefinition getSkill()
    {
        return skill;
    }
}
