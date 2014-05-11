package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
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
import org.pilsgeschwader.furryironman.model.eve.EvESkillDefinition;
import org.pilsgeschwader.furryironman.model.eve.EvESkillInTrainingInfo;
import org.pilsgeschwader.furryironman.view.common.Util;

/**
 *
 * @author binarygamura
 */
public class EveCharacterRenderer extends JPanel implements ListCellRenderer<EvECharacter>
{    
    private final JLabel leftImageIcon;
    
    private final JLabel rightImageIcon;
    
    private final JLabel textLabel;
    
    private final Controller controller;
    
    private static final Logger logger = Logger.getLogger(EveCharacterRenderer.class.getName());
    
    public static final int DEFAULT_IMAGE_SIZE = 64;
    
    private final NumberFormat numberFormat;
    
    private final DateFormat durationFormat;
    
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
        numberFormat = Util.getNiceNumberFormat();
        durationFormat = new SimpleDateFormat("D'd' h'h' m'm' s's'");
        durationFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends EvECharacter> list, EvECharacter character, int index, boolean isSelected, boolean cellHasFocus)
    {
        String text = "<html><b>"+character.getCharacterName()+"</b><br><i>"+character.getCorporationName()+"</i>";
        EvESkillInTrainingInfo info = character.getInfo();
        if(info != null)
        {
            text += "<br>";
            EvESkillDefinition definition = info.getSkill();
            if(definition == null)
            {
                text += "no skill";
            }
            else
            {
                
                long skillpoints = info.getTrainingDestinationSP() - info.getTrainingStartSP();
                Date refDate = new Date();
                long duration =  (info.getTrainingEndTime().getTime() - info.getTrainingStartTime().getTime());
                long elapsedTime = (refDate.getTime() - info.getTrainingStartTime().getTime());
                double ratio = (double) elapsedTime / (double) duration;
                long totalSkillPoints = (long) (info.getTrainingStartSP() + skillpoints * ratio);
                
                
                
                text += definition.getTypeName()+" ("+numberFormat.format(totalSkillPoints)+"/"+numberFormat.format(info.getTrainingDestinationSP())+")";
                text +=  " "+durationFormat.format(info.getTrainingEndTime().getTime() - (refDate.getTime()));
            }            
        }
        try
        {
            Image image = controller.characterImageController.fetchImageFor(character.getCharacterID(), DEFAULT_IMAGE_SIZE);
            leftImageIcon.setIcon(image == null ? new ImageIcon() : new ImageIcon(image));
        }
        catch(IOException | ControllerException ex)
        {
            logger.log(Level.SEVERE, "unable to fetch character image for \"{0}\".", character.getCharacterID());
            leftImageIcon.setIcon(new ImageIcon());
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
