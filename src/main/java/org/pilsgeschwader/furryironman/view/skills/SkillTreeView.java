package org.pilsgeschwader.furryironman.view.skills;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.pilsgeschwader.furryironman.model.eve.EvESkillDefinition;
import org.pilsgeschwader.furryironman.model.eve.EvESkillTree;
import org.pilsgeschwader.furryironman.model.eve.EvESkillTreeGroup;
import org.pilsgeschwader.furryironman.view.icons.IconCache;
import org.pilsgeschwader.furryironman.view.icons.IconNames;

/**
 *
 * @author binary gamura
 */
public class SkillTreeView extends JTree
{
    private final EvESkillTree skilltree;
    
    public SkillTreeView(EvESkillTree skilltree)
    {
        super();
        this.skilltree = skilltree;
        setCellRenderer(new Renderer());
        filter("");
    }
    
    public void filter(String filter)
    {
        DefaultMutableTreeNode groupNode;
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("skilltree");
        for(EvESkillTreeGroup group : skilltree)
        {
            groupNode = createGroupNode(group, filter);
            if(groupNode != null)
            {
                rootNode.add(groupNode);
            }
        }
        setModel(new DefaultTreeModel(rootNode));
        revalidate();
    }
    
    private List<EvESkillDefinition> getMatches(EvESkillTreeGroup group, String filter)
    {
        List<EvESkillDefinition> matches = new ArrayList<>();
        for(EvESkillDefinition definition : group)
        {
            if(filter.isEmpty() || definition.getTypeName().toLowerCase().contains(filter))
            {
                matches.add(definition);
            }
        }
        return matches;
    }
    
    private DefaultMutableTreeNode createGroupNode(EvESkillTreeGroup group, String filter)
    {
        DefaultMutableTreeNode groupNode = null;
        List<EvESkillDefinition> matches = getMatches(group, filter);
        if(!matches.isEmpty())
        {
            groupNode = new DefaultMutableTreeNode(new SkillTreeViewGroup(group), true);
            for(EvESkillDefinition skill : matches)
            {
                groupNode.add(createSkillNode(skill));
            }
        }        
        return groupNode;
    }
    
    private DefaultMutableTreeNode createSkillNode(EvESkillDefinition skill)
    {
        return new DefaultMutableTreeNode(new SkillTreeViewNode(skill), false);
    }
    
    private class Renderer extends DefaultTreeCellRenderer
    {
        public Renderer()
        {
            setLeafIcon(IconCache.getIcon(IconNames.SKILLBOOK));
            setRootVisible(false);
            setShowsRootHandles(true);
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
        {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            StringBuilder buffer = new StringBuilder();
            if(value instanceof DefaultMutableTreeNode)
            {
                value = ((DefaultMutableTreeNode) value).getUserObject();
                
                if(value instanceof SkillTreeViewNode)
                {
                    SkillTreeViewNode node = (SkillTreeViewNode) value;
                    buffer.append(node.getSkill().getTypeName());
                    buffer.append(" (").append(node.getSkill().getRank()).append("x)");
                }
                else if(value instanceof SkillTreeViewGroup)
                {
                    SkillTreeViewGroup group = (SkillTreeViewGroup) value;
                    buffer.append(group.getGroup().getGroupname());
                    buffer.append(" (").append(group.getGroup().getSkills().size()).append(" skills)");
                }
                setText(buffer.toString());
            }            
            else
            {
                setText(value.toString());
            }                        
            return this;
        }
    }
}
