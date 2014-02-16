/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.view.skills;

import org.pilsgeschwader.furryironman.model.eve.EvESkillTreeGroup;

/**
 *
 * @author binary gamura
 */
public class SkillTreeViewGroup 
{
    private final EvESkillTreeGroup group;
    
    public SkillTreeViewGroup(EvESkillTreeGroup group)
    {
        this.group = group;
    }

    
    
    public EvESkillTreeGroup getGroup()
    {
        return group;
    }
}
