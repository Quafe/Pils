package org.pilsgeschwader.furryironman.view.common;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author boreas
 */
public class ButtonPanel extends JPanel
{
    private final List<JButton> buttons;
    
    public ButtonPanel()
    {
        super(new GridLayout(1, 0));
        setBorder(BorderFactory.createRaisedBevelBorder());
        buttons = new ArrayList<>();
    }
       
    @Override
    public void setEnabled(boolean enabled)
    {
//        super.setEnabled(enabled);
        for(JButton button : buttons)
        {
            button.setEnabled(enabled);
        }
    }
    
    public void addButton(JButton button)
    {
        if(!buttons.contains(button))
        {
            buttons.add(button);
            add(button);
        }
    }
}
