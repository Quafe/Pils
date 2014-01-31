package org.pilsgeschwader.furryironman.view.common;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author boreas
 */
public class SimpleForm extends JPanel
{
    private final GridBagConstraints c;
    
    private final List<JComponent> components;
    
    public SimpleForm()
    {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridy = -1;
        c.gridx = 0;
        c.insets = new Insets(5, 5, 5, 5);
        components = new ArrayList<>();
    }
    
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        for(JComponent component : components)
        {
            component.setEnabled(enabled);
        }
    }
    
    public void addRow(String label, JComponent... components)
    {
        c.gridy++;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("<html><b>"+label+"</b>"), c);
        
        c.anchor = GridBagConstraints.LINE_START;
        for(JComponent component : components)
        {
            if(!this.components.contains(component))
            {
                c.gridx++;
                add(component, c);
                this.components.add(component);
            }
        }
    }
}
