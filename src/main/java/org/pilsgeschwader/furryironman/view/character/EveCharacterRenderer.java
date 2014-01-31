package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import org.pilsgeschwader.furryironman.controller.common.Controller;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;

/**
 *
 * @author boreas
 */
public class EveCharacterRenderer extends JPanel implements ListCellRenderer<EvECharacter>
{    
    private final JLabel leftImageIcon;
    
    private final JLabel rightImageIcon;
    
    private final JLabel textLabel;
    
    private final Controller controller;
    
    private static final Logger logger = Logger.getLogger(EveCharacterRenderer.class.getName());
    
    public static final int DEFAULT_IMAGE_SIZE = 64;
    
    public EveCharacterRenderer(Controller controller)
    {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.controller = controller;
        leftImageIcon = new JLabel();
        rightImageIcon = new JLabel();
        textLabel = new JLabel();
        textLabel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(leftImageIcon, BorderLayout.WEST);
        add(rightImageIcon, BorderLayout.EAST);
        add(textLabel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends EvECharacter> list, EvECharacter character, int index, boolean isSelected, boolean cellHasFocus)
    {
        String text = "<html><b>"+character.getCharacterName()+"</b><br><i>"+character.getCorporationName()+"</i>";
        
        try
        {
            leftImageIcon.setIcon(new ImageIcon(controller.characterImageController.fetchImageFor(character.getCharacterID(), DEFAULT_IMAGE_SIZE)));
        }
        catch(IOException | ControllerException ex)
        {
            logger.log(Level.SEVERE, "unable to fetch character image for \"{0}\".", character.getCharacterID());
        }
        
        try
        {
            rightImageIcon.setIcon(new ImageIcon(controller.corpImageController.fetchImageFor(character.getCorporationID(), DEFAULT_IMAGE_SIZE)));
        }
        catch(IOException | ControllerException ex)
        {
            logger.log(Level.SEVERE, "unable to fetch character image for \"{0}\".", character.getCharacterID());
        }
        
        textLabel.setText(text);
        
        if (isSelected) 
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else 
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }    
}
