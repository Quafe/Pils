package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.model.eve.EvECharacterSheet;
import org.pilsgeschwader.furryironman.view.FurryIronman;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;
import org.pilsgeschwader.furryironman.view.common.RunnableActionListener;
import org.pilsgeschwader.furryironman.view.icons.IconCache;
import org.pilsgeschwader.furryironman.view.icons.IconNames;
import org.xml.sax.SAXException;

/**
 *
 * @author binarygamura
 */
public class CharacterSheetDialog extends AbstractDialog implements Runnable
{
    private final FurryIronman parent;
    
    private JTabbedPane pane;
    
    private final EvECharacter character;
    
    private EvECharacterSheet sheet;
    
    private final List<AbstractCharacterSheetDialogApp> apps;
    
    public CharacterSheetDialog(FurryIronman parent, EvECharacter character)
    {
        super("Character Sheet", parent);
        setModal(false);
        apps = new ArrayList<>();
        this.parent = parent;
        this.character = character;
        init();
    }

    public FurryIronman getParentApp()
    {
        return parent;
    }
    
    
    
    private void addApp(AbstractCharacterSheetDialogApp app)
    {
        if(!apps.contains(app))
        {
            pane.add(app.getTitle(), app);
            apps.add(app);
        }
    }
    
    public void reloadData() throws ControllerException, IOException, ParserConfigurationException, SAXException
    {
//        SwingUtilities.invokeLater(this);
        parent.threadPool.submit(this);
    }
    
    private void init()
    {
        
        addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowOpened(WindowEvent e)
            {
                try
                {
                    reloadData();
                }
                catch(IOException | ParserConfigurationException | ControllerException | SAXException ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
        });
        
        pane = new JTabbedPane();
        pane.setTabPlacement(JTabbedPane.LEFT);
        add(pane, BorderLayout.CENTER);
        
        JButton reloadButton = new JButton("reload", IconCache.getIcon(IconNames.RELOAD));
        reloadButton.addActionListener(new RunnableActionListener(this));
        
        buttonPanel.addButton(reloadButton);
        addDefaultCloseButton();
        
        addApp(new CharacterOverviewApp(this));
        addApp(new CharacterSheetSkillsApp(this));
        
        setMinimumSize(new Dimension(640, 480));
        validate();
        pack();
        setLocationRelativeTo(parent); 
    }

    @Override
    public void run()
    {
        try
        {
            startProgressBar("loading charcter sheet...");
            buttonPanel.setEnabled(false);
            sheet = parent.getController().loadCharacterSheet(character);
            for(AbstractCharacterSheetDialogApp app : apps)
            {
                app.onData(sheet);
            }
            stopProgressBar("done loading character sheet");
        }
        catch (ControllerException | SAXException  | ParserConfigurationException | IOException ex)
        {
            showError("error", ex.getMessage());
            ex.printStackTrace(System.err);
        } 
        finally
        {
            stopProgressBar(null);
            buttonPanel.setEnabled(true);
        }
    }
}
