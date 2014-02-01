package org.pilsgeschwader.furryironman.view.apikey;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JLabel;
import org.pilsgeschwader.furryironman.controller.common.XMLApiRequest;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;
import org.pilsgeschwader.furryironman.view.FurryIronman;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;
import org.pilsgeschwader.furryironman.view.common.SimpleForm;

/**
 *
 * @author binarygamura
 */
public class ApiKeyInfoDialog extends AbstractDialog implements Runnable
{
    private final ApiKey key;
    
    private FurryIronman parent;
    
    public ApiKeyInfoDialog(ApiKey key, FurryIronman parent)
    {
        super("key info <"+key.getKeyId()+">", parent);
        this.parent = parent;
        setModal(true);
        this.key = key;
        init();
    }
    
    private void init()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        SimpleForm form = new SimpleForm();
        form.addRow("access mask:", new JLabel(String.valueOf(key.getAccessMask())));
        form.addRow("expires:", new JLabel(format.format(key.getValidTo())));
        List<XMLApiRequest.Target> matching = XMLApiRequest.Target.getMatching(key);
        for(XMLApiRequest.Target target : XMLApiRequest.Target.values())
        {
            form.addRow(target.getDescription()+":", new JLabel(matching.contains(target) ? "true" : "false"));
        }
        
        add(form, BorderLayout.CENTER);
        validate();
        pack();
    }
    
    public void reload()
    {
        parent.threadPool.execute(this);
    }

    @Override
    public void run()
    {
        try
        {
            startProgressBar("loading key info...");
        }
        catch(Exception ex)
        {
            
        }
        finally
        {
            
        }
    }
    
}
