package org.pilsgeschwader.combatmapper.view.apikey;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.pilsgeschwader.combatmapper.controller.common.XMLApiRequest;
import org.pilsgeschwader.combatmapper.model.eve.ApiKey;
import org.pilsgeschwader.combatmapper.view.EvECombatMapper;
import org.pilsgeschwader.combatmapper.view.common.AbstractDialog;
import org.pilsgeschwader.combatmapper.view.common.SimpleForm;

/**
 *
 * @author boreas
 */
public class ApiKeyInfoDialog extends AbstractDialog implements Runnable
{
    private final ApiKey key;
    
    public ApiKeyInfoDialog(ApiKey key, JFrame parent)
    {
        super("key info <"+key.getKeyId()+">", parent);
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
        EvECombatMapper.threadPool.execute(this);
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
