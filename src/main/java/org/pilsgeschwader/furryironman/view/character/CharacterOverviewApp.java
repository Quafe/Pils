/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;
import org.pilsgeschwader.furryironman.view.common.SimpleForm;
import org.pilsgeschwader.furryironman.view.common.Util;

/**
 *
 * @author binarygamura
 */
public class CharacterOverviewApp extends AbstractCharacterSheetDialogApp
{

    private JTextField nameField;  
    
    private JTextField raceField;
    
    private JTextField bloodlineField;
    
    private JTextField genderField;
    
    private JTextField cloneNameField;
    
    private JTextField cloneSkillPointsField;
    
    private JTextField skillPointsField;
    
    private JTextField numberOfSkillField;
    
    private JTextField balanceField;
    
    private JTextField corpnameField;
    
    private JLabel imageLabel;
    
    private static final int ICON_SIZE = 256;
    
    
    
//    private JTextField willpowerField;
//    private JTextField intelligenceField;
//    private JTextField perceptionField;
//    private JTextField charismaField;
//    private JTextField memoryField;
    
    
    
    public CharacterOverviewApp(CharacterSheetDialog parent)
    {
        super(parent);
        title = "overview";
        init();
    }
    
    private void init()
    {
        nameField = new JTextField(20);
        nameField.setEditable(false);
        raceField = new JTextField(20);
        raceField.setEditable(false);
        bloodlineField = new JTextField(20);
        bloodlineField.setEditable(false);
        genderField = new JTextField(20);
        genderField.setEditable(false);
        cloneNameField = new JTextField(20);
        cloneNameField.setEditable(false);
        cloneSkillPointsField = new JTextField(20);
        cloneSkillPointsField.setEditable(false);
        skillPointsField = new JTextField(20);
        skillPointsField.setEditable(false);
        balanceField = new JTextField(20);
        balanceField.setEditable(false);
        corpnameField = new JTextField(20);
        corpnameField.setEditable(false);
        numberOfSkillField = new JTextField(20);
        numberOfSkillField.setEditable(false);
        
        
        
        SimpleForm form = new SimpleForm();
        form.addRow("name:", nameField);        
        form.addRow("corp:", corpnameField);
        form.addRow("skillpoints:", skillPointsField);
        form.addRow("skills:", numberOfSkillField);
        form.addRow("bloodline:", bloodlineField);
        form.addRow("gender:", genderField);
        form.addRow("race:", raceField);        
        form.addRow("clone:", cloneNameField);
        form.addRow("clone skill points:", cloneSkillPointsField);
        form.addRow("balance:", balanceField);
        
        
        imageLabel = new JLabel();
        imageLabel.setMinimumSize(new Dimension(ICON_SIZE, ICON_SIZE));
        imageLabel.setMaximumSize(new Dimension(ICON_SIZE, ICON_SIZE));
        imageLabel.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
        imageLabel.setSize(new Dimension(ICON_SIZE, ICON_SIZE));
        
//        JPanel topWrapper = new JPanel();
//        topWrapper.setLayout(new BorderLayout());
//        topWrapper.add(imageLabel, BorderLayout.CENTER);
        
        add(imageLabel, BorderLayout.WEST);
        add(form, BorderLayout.CENTER);
        
        
    }

    @Override
    public void onReload()
    {
        
    }

    @Override
    public void onData(EvECharacterSheet sheet)
    {
        try
        {
            DecimalFormat format = new DecimalFormat("#");
            NumberFormat iskFormat = Util.getIskNumberFormatter();
            try
            {
                    
                Image image = parent.getParentApp().getController().characterImageController.fetchImageFor(sheet.getCharacterID(), ICON_SIZE);
                imageLabel.setIcon(new ImageIcon(image));
            }
            catch(ControllerException ex)
            {
                imageLabel.setIcon(new ImageIcon());
            }
            corpnameField.setText(sheet.getCorporationName());
            nameField.setText(sheet.getName());
            raceField.setText(sheet.getRace());
            bloodlineField.setText(sheet.getBloodline());
            genderField.setText(sheet.getGender());
            cloneNameField.setText(sheet.getCloneName());
            cloneSkillPointsField.setText(format.format(sheet.getCloneSkillPoints()));
            balanceField.setText(iskFormat.format(sheet.getBalance()));
            numberOfSkillField.setText(sheet.getSkills() == null ? "unknown" : String.valueOf(sheet.getSkills().size()));
            skillPointsField.setText(format.format(sheet.getSkillPoints()));
            
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace(System.err);
        }
    }
}
