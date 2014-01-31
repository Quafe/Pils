package org.pilsgeschwader.combatmapper.view.character;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.combatmapper.controller.common.ControllerException;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacter;
import org.pilsgeschwader.combatmapper.model.eve.EvECharacterStatus;
import org.pilsgeschwader.combatmapper.view.EvECombatMapper;
import org.pilsgeschwader.combatmapper.view.common.AbstractDialog;
import org.pilsgeschwader.combatmapper.view.common.SimpleForm;
import org.xml.sax.SAXException;

/**
 *
 * @author boreas
 */
public class CharacterStatusDialog extends AbstractDialog implements ActionListener, Runnable
{
    private JTextField loginCountField;
    
    private JTextField logonMinutesField;
    
    private JTextField paidUntilField;
    
    private JTextField createDateField;
    
    private final EvECombatMapper parent;
    
    private final EvECharacter character;
    
    private final SimpleDateFormat dateFormat;
    
    public CharacterStatusDialog(EvECombatMapper parent, EvECharacter character)
    {
        super(character.getCharacterName(), parent);
        setModal(false);
        this.character = character;
        this.parent = parent;
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        init();        
    }
    
    private void init()
    {
        JButton reloadButton = new JButton("reload");
        reloadButton.addActionListener(this);
        
        buttonPanel.addButton(reloadButton);
        
        loginCountField = new JTextField(14);
        loginCountField.setEditable(false);
        loginCountField.setHorizontalAlignment(JTextField.RIGHT);
        logonMinutesField = new JTextField(14);
        logonMinutesField.setHorizontalAlignment(JTextField.RIGHT);
        logonMinutesField.setEditable(false);
        paidUntilField = new JTextField(14);
        paidUntilField.setHorizontalAlignment(JTextField.RIGHT);
        paidUntilField.setEditable(false);
        createDateField = new JTextField(14);
        createDateField.setHorizontalAlignment(JTextField.RIGHT);
        createDateField.setEditable(false);
        
        SimpleForm form = new SimpleForm();
        form.addRow("login count:", loginCountField);
        form.addRow("logon minutes:", logonMinutesField, new JLabel("min."));
        form.addRow("paid until:", paidUntilField);
        form.addRow("created:", createDateField);
        
        add(form, BorderLayout.CENTER);
        
        validate();
        pack();
        setLocationRelativeTo(parent);
        
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        EvECombatMapper.threadPool.execute(this);
    }

    @Override
    public void run()
    {
        try
        {
            buttonPanel.setEnabled(false);
            startProgressBar("loading status...");
            EvECharacterStatus characterStatus = parent.getController().characterController.getCharacterStatus(character);
            loginCountField.setText(String.valueOf(characterStatus.getLogonCount()));
            logonMinutesField.setText(String.valueOf(characterStatus.getLoginMinutes()));
            paidUntilField.setText(characterStatus.getPaidUntil() == null ? "unknown" : dateFormat.format(characterStatus.getPaidUntil()));
            createDateField.setText(characterStatus.getCreateDate() == null ? "unknown" : dateFormat.format(characterStatus.getCreateDate()));
        }
        catch(URISyntaxException | IOException | ParserConfigurationException | SAXException | ControllerException ex)
        {
            showError("error", ex.getMessage());
            ex.printStackTrace(System.err);
        }
        finally
        {
            buttonPanel.setEnabled(true);
            stopProgressBar("done...");
        }
    }
}
