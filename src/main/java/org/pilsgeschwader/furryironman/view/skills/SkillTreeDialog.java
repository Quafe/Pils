package org.pilsgeschwader.furryironman.view.skills;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.pilsgeschwader.furryironman.model.eve.EvESkillTree;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;
import org.pilsgeschwader.furryironman.view.icons.IconCache;
import org.pilsgeschwader.furryironman.view.icons.IconNames;

/**
 *
 * @author binary gamura
 */
public class SkillTreeDialog extends AbstractDialog implements Runnable, TreeSelectionListener
{
    private final EvESkillTree skilltree;
    
    private JTextField searchField;
    
    private SkillInfoPanel infopanel;
    
    private SkillTreeView view;
    
    public SkillTreeDialog(JFrame parent, EvESkillTree skilltree)
    {
        super("skill tree", parent);
        this.skilltree = skilltree;
        setModal(false);
        init();
    }
    
    private void init()
    {        
        view = new SkillTreeView(skilltree);
        view.addTreeSelectionListener(this);
        infopanel = new SkillInfoPanel();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(view), new JScrollPane(infopanel));
        
        
        
        JButton searchButton = new JButton("search", IconCache.getIcon(IconNames.SEARCH));
        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                view.filter(searchField.getText().toLowerCase().trim());
            }
        });
        
        searchField = new JTextField();
        searchField.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                view.filter(searchField.getText().toLowerCase().trim());
            }
        });
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(new JLabel("filter:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        
        add(splitPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        
        addDefaultCloseButton();
        setResizable(true);
        setMinimumSize(new Dimension(640, 480));
        validate();
        pack();
        setLocationRelativeTo(getParent());
    }
    
    @Override
    public void run()
    {
        
    }

    @Override
    public void valueChanged(TreeSelectionEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.getLastSelectedPathComponent();
        if(node != null)
        {
            Object source = node.getUserObject();
            if(source instanceof SkillTreeViewNode)
            {
                SkillTreeViewNode skillNode = (SkillTreeViewNode) source;
                infopanel.setData(skillNode.getSkill(), skilltree.getIdToSkill());
            }
        }
    }
}
