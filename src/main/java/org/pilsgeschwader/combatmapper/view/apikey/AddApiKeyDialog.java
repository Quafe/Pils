package org.pilsgeschwader.combatmapper.view.apikey;

import org.pilsgeschwader.combatmapper.view.common.AbstractDialog;
import org.pilsgeschwader.combatmapper.view.common.RunnableActionListener;
import org.pilsgeschwader.combatmapper.view.common.SimpleForm;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.combatmapper.controller.common.Controller;
import org.pilsgeschwader.combatmapper.controller.common.ControllerException;
import org.pilsgeschwader.combatmapper.model.eve.ApiKey;
import org.xml.sax.SAXException;

/**
 *
 * @author boreas
 */
public class AddApiKeyDialog extends AbstractDialog implements Runnable
{
    private SimpleForm form;    
    
    private JTextField vCodeField;
    
    private JTextField keyId;
    
    private final ApiKeyManager manager;
    
    public AddApiKeyDialog(ApiKeyManager manager)
    {
        super("add api key", manager);
        this.manager = manager;
        init();
    }
    
    private void init()
    {
        form = new SimpleForm();
        
        JButton addButton = new JButton("add");
        addButton.addActionListener(new RunnableActionListener(this));
        
        buttonPanel.addButton(addButton);
        addDefaultCloseButton();
        
        vCodeField = new JTextField(30);
        keyId = new JTextField(30);
        
        form.addRow("key id:", keyId);
        form.addRow("vCode:", vCodeField);
        
        add(form, BorderLayout.CENTER);
        validate();
        pack();
    }

    @Override
    public void run()
    {
        startProgressBar("checking key...");
        buttonPanel.setEnabled(false);
        try
        {
            ApiKey key = new ApiKey(Integer.valueOf(keyId.getText().trim()), vCodeField.getText());
            Controller controller = manager.getParentGUI().getController();
            controller.keyController.validate(key);
            controller.getModel().getStoredKeys().add(key);
            dispose();
        }
        catch(NumberFormatException ex)
        {
            showError("keyid not valid", "the key id needs to be an integer number.");
        }
        catch(ControllerException ex)
        {
            showError("key invalid", "please check your key. your data is invalid.");
        }
        catch(IOException | URISyntaxException | ParserConfigurationException | SAXException ex)
        {
            showError("key invalid", "please check your key. your data is invalid.");
        }
        finally
        {
            buttonPanel.setEnabled(true);
            stopProgressBar("done..");
        }
        
    }
}
