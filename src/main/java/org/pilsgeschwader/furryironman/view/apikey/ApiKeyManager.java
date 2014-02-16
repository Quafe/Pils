package org.pilsgeschwader.furryironman.view.apikey;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.pilsgeschwader.furryironman.view.FurryIronman;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;
import org.pilsgeschwader.furryironman.view.icons.IconCache;
import org.pilsgeschwader.furryironman.view.icons.IconNames;

/**
 *
 * @author binarygamura
 */
public class ApiKeyManager extends AbstractDialog
{
    private final FurryIronman parentGUI;
    
    private JList<ApiKey> keysList;
    
    private final AddApiKeyDialog addDialog;
    
    public ApiKeyManager(FurryIronman parentGUI)
    {
        super("manage api keys", parentGUI);
        setIconImage(IconCache.getIcon(IconNames.KEY).getImage());
        this.parentGUI = parentGUI;
        addDialog = new AddApiKeyDialog(ApiKeyManager.this);
        addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowActivated(WindowEvent e)
            {
                reloadList();
            }
        });
        init();
    }

    public FurryIronman getParentGUI()
    {
        return parentGUI;
    }
    
    private void reloadList()
    {
        DefaultListModel<ApiKey> model = (DefaultListModel<ApiKey>) (keysList.getModel());
        model.removeAllElements();
        for(ApiKey key : parentGUI.getController().getModel().getStoredKeys())
        {
            model.addElement(key);
        }
        repaint();
    }
    
    private void init()
    {
        
        JButton closeButton = new JButton("close", IconCache.getIcon(IconNames.CANCEL));
        closeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        
        JButton addButton = new JButton("add", IconCache.getIcon(IconNames.ADD));
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addDialog.setLocationRelativeTo(ApiKeyManager.this);
                addDialog.clearFields();
                addDialog.setVisible(true);
                reloadList();
            }
        });
        JButton removeButton = new JButton("remove", IconCache.getIcon(IconNames.REMOVE));
        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ApiKey selected = keysList.getSelectedValue();
                if(selected != null)
                {
                    if(askYesNoQuestion("remove key?", "do you really want to remove the selected key?"))
                    {
                        parentGUI.getController().getModel().getStoredKeys().remove(selected);
                        reloadList();
                    }
                }
            }
        });
        JButton infoButton = new JButton("info", IconCache.getIcon(IconNames.INFO));
        infoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ApiKey selected = keysList.getSelectedValue();
                if(selected != null)
                {
                    ApiKeyInfoDialog apiKeyInfoDialog = new ApiKeyInfoDialog(selected, parentGUI);
                    apiKeyInfoDialog.setLocationRelativeTo(ApiKeyManager.this);
                    apiKeyInfoDialog.setVisible(true);
                }
                
            }
        });
        
        keysList = new JList<>();
        keysList.setCellRenderer(new ApiKeyRenderer());
        keysList.setModel(new DefaultListModel<ApiKey>());
                
        add(new JScrollPane(keysList), BorderLayout.CENTER);
        
        buttonPanel.addButton(addButton);
        buttonPanel.addButton(removeButton);
        buttonPanel.addButton(infoButton);
        buttonPanel.addButton(closeButton);        
        
        validate();
        pack();
        setLocationRelativeTo(parentGUI);
        
    }
}
