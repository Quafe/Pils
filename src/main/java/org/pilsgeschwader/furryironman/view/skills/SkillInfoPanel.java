/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.view.skills;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.pilsgeschwader.furryironman.model.eve.EvESkillBonus;
import org.pilsgeschwader.furryironman.model.eve.EvESkillDefinition;
import org.pilsgeschwader.furryironman.model.eve.EveSkillTreeRequirement;

/**
 *
 * @author binary gamura
 */
public class SkillInfoPanel extends JPanel
{
    private EvESkillDefinition definition;
    
    private JPanel attributesPanel;
    
    private JPanel requiredSkillsPanel;
    
    private JLabel titleLabel;
    
    private JLabel descriptionLabel;
    
    
    
    public SkillInfoPanel()
    {
        super(new BorderLayout());
        init();
    }
    
    public void setData(EvESkillDefinition definition, Map<Integer, EvESkillDefinition> skillsIndex)
    {
        this.definition = definition;
        titleLabel.setText("<html><u><h1>"+definition.getTypeName());
        descriptionLabel.setText("<html><i>"+definition.getDescription());
        
        EvESkillDefinition skill;
        requiredSkillsPanel.removeAll();
        for(EveSkillTreeRequirement requirement : definition.getRequirements())
        {
            skill = skillsIndex.get(requirement.getTypeID());
            requiredSkillsPanel.add(new SkillRequirementsLabel(requirement, skill));
        }
        
        attributesPanel.removeAll();
        for(EvESkillBonus bonus : definition.getBonis())
        {
            attributesPanel.add(new JLabel(bonus.getType().name()));
        }
        revalidate();
    }
    
    private void init()
    {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        titleLabel = new JLabel();
        titleLabel.setMinimumSize(new Dimension(10, 60));
        titleLabel.setPreferredSize(new Dimension(10, 60));
        descriptionLabel = new JLabel();
        descriptionLabel.setBorder(BorderFactory.createTitledBorder("description:"));
        descriptionLabel.setMinimumSize(new Dimension(10, 80));
        descriptionLabel.setPreferredSize(new Dimension(10, 80));
        
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.add(titleLabel, BorderLayout.NORTH);
        topWrapper.add(descriptionLabel, BorderLayout.CENTER);
        
        requiredSkillsPanel = new JPanel(new GridLayout(0, 1));
        requiredSkillsPanel.setBorder(BorderFactory.createTitledBorder("requirements:"));
        JPanel requiredSkillsPanelWrapper = new JPanel(new BorderLayout());
        requiredSkillsPanelWrapper.add(requiredSkillsPanel, BorderLayout.NORTH);
        
        attributesPanel = new JPanel(new GridLayout(0, 1));
        attributesPanel.setBorder(BorderFactory.createTitledBorder("attributes:"));
        JPanel attributesPanelWrapper = new JPanel(new BorderLayout());
        attributesPanelWrapper.add(attributesPanel, BorderLayout.NORTH);
        
        JPanel centerpanel = new JPanel(new GridLayout(1, 2));
        centerpanel.add(requiredSkillsPanelWrapper);
        centerpanel.add(attributesPanelWrapper);
        
        
        
        
        add(topWrapper, BorderLayout.NORTH);
        add(centerpanel, BorderLayout.CENTER);
    }
    
    static class SkillRequirementsLabel extends JLabel
    {
        private final EveSkillTreeRequirement requirement;
        
        private final EvESkillDefinition definition;
        
        public SkillRequirementsLabel(EveSkillTreeRequirement requirement, EvESkillDefinition definition)
        {
            this.requirement = requirement;
            this.definition = definition;
            init();
        }
        
        private void init()
        {
            setText("<html><b>"+definition.getTypeName()+" "+requirement.getSkillLevel()+"</b>");
        }
    }
}
