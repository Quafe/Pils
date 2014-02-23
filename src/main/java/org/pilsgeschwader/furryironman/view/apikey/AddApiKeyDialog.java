package org.pilsgeschwader.furryironman.view.apikey;

import org.pilsgeschwader.furryironman.view.common.AbstractDialog;
import org.pilsgeschwader.furryironman.view.common.RunnableActionListener;
import org.pilsgeschwader.furryironman.view.common.SimpleForm;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.Controller;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.pilsgeschwader.furryironman.view.icons.IconCache;
import org.pilsgeschwader.furryironman.view.icons.IconNames;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
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
        setIconImage(IconCache.getIcon(IconNames.KEY).getImage());
        this.manager = manager;
        init();
    }
    
    public void clearFields()
    {
        keyId.setText("");
        vCodeField.setText("");
    }
    
    
    private void init()
    {
        form = new SimpleForm();
        
        JButton addButton = new JButton("add", IconCache.getIcon(IconNames.ADD));
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
            ApiKey key = new ApiKey(Integer.parseInt(keyId.getText().trim()), vCodeField.getText());
            Controller controller = manager.getParentGUI().getController();
            controller.keyController.validate(key);
            if(!controller.getModel().addApiKey(key))
            {
                showError("key skipped", "the key "+key.getKeyId()+" is already stored.");
            }
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
        catch(IOException | URISyntaxException | ParseException | ParserConfigurationException | SAXException ex)
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
