package org.pilsgeschwader.furryironman.view;

import org.pilsgeschwader.furryironman.view.character.CharacterStatusDialog;
import org.pilsgeschwader.furryironman.view.character.EveCharacterRenderer;
import org.pilsgeschwader.furryironman.view.apikey.ApiKeyManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;
import org.pilsgeschwader.furryironman.controller.common.Controller;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.controller.common.PropertyNotFoundException;
import org.pilsgeschwader.furryironman.model.app.ApplicationConfig;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.view.character.CharacterSheetDialog;
import org.pilsgeschwader.furryironman.view.character.SaveCharacterImageDialog;
import org.pilsgeschwader.furryironman.view.common.ButtonPanel;
import org.pilsgeschwader.furryironman.view.common.RunnableActionListener;
import org.pilsgeschwader.furryironman.view.icons.IconCache;
import org.pilsgeschwader.furryironman.view.icons.IconNames;
import org.pilsgeschwader.furryironman.view.skills.SkillTreeDialog;
import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;
import org.xml.sax.SAXException;

/**
 * 
 */
public class FurryIronman extends JFrame implements Runnable
{
    private JProgressBar progressBar;
    
    private final Controller controller;
    
    private static final Logger logger = Logger.getLogger(FurryIronman.class.getName());
    
    public final ExecutorService threadPool = Executors.newCachedThreadPool();
    
    private final ApiKeyManager apiKeyManager;
    
    private JList<EvECharacter> allCharactersList;
    
    private ButtonPanel buttonPanel;
    
    private final RenderThread renderThread;
    
    private FurryIronman() throws URISyntaxException
    {
        super("EveCombatMapper");
        setIconImage(IconCache.getIcon(IconNames.ICON).getImage());
        controller = new Controller();
        apiKeyManager = new ApiKeyManager(this);
        renderThread = new RenderThread();
    }
    
    public Controller getController()
    {
        return controller;
    }
    
    private void exitOperation()
    {
        int result = JOptionPane.showConfirmDialog(this, "do you really want to quit?", "quit?", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION)
        {
            renderThread.stop();
            controller.shutDown();
            threadPool.shutdown();
            dispose();            
        }
    }
    
    private void createJMenuBar()
    {
        JMenuItem quitButton = new JMenuItem("quit", IconCache.getIcon(IconNames.CANCEL));
        quitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                exitOperation();
            }
        });
        JMenu fileMenu = new JMenu("file");
        fileMenu.add(quitButton);
        
        
        JMenuItem skilltreeButton = new JMenuItem("skill tree");
        skilltreeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SkillTreeDialog dialog = new SkillTreeDialog(FurryIronman.this, controller.getModel().getSkilltree());
                dialog.setLocationRelativeTo(FurryIronman.this);
                dialog.setVisible(true);
            }
        });
        
        JMenuItem saveCharImageMenu = new JMenuItem("save avatar");
        saveCharImageMenu.addActionListener(new RunnableActionListener(new Runnable()
        {

            @Override
            public void run()
            {
                SaveCharacterImageDialog dialog = new SaveCharacterImageDialog(FurryIronman.this);
                dialog.setVisible(true);
            }
        }, true));
        
        JMenuItem toolMenu = new JMenu("tools");
        toolMenu.add(skilltreeButton);
        toolMenu.add(saveCharImageMenu);
        
        JMenuItem manageApiKeysButton = new JMenuItem("api keys", IconCache.getIcon(IconNames.KEY));
        manageApiKeysButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                apiKeyManager.setLocationRelativeTo(FurryIronman.this);
                apiKeyManager.setVisible(true);
            }
        });
        
        JMenu optionsMenu = new JMenu("options");
        optionsMenu.add(manageApiKeysButton);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(toolMenu);
        menuBar.add(optionsMenu);
        
        setJMenuBar(menuBar);
    }
    
    private FurryIronman init() throws IOException, ClassNotFoundException, SQLException, PropertyNotFoundException, URISyntaxException, ParserConfigurationException, SAXException, ControllerException, ParseException
    {
        ApplicationConfig config = new ApplicationConfig();
        controller.init(config.load(new File("./config.properties")));
        
        createJMenuBar();
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                exitOperation();
            }
        });        
        
        JButton reloadAllCharactersButton = new JButton("reload", IconCache.getIcon(IconNames.RELOAD));
        reloadAllCharactersButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                threadPool.execute(FurryIronman.this);
            }
        });
        
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("idle...");
        
        JButton statusButton = new JButton("status", IconCache.getIcon(IconNames.STATS));
        statusButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                EvECharacter selected = allCharactersList.getSelectedValue();
                if(selected != null)
                {
                    CharacterStatusDialog dialog = new CharacterStatusDialog(FurryIronman.this, selected);
                    dialog.setVisible(true);
                    threadPool.submit(dialog);
                }
            }
        });
        
        JButton characterInfoButton = new JButton("info", IconCache.getIcon(IconNames.INFO));
        characterInfoButton.addActionListener(new RunnableActionListener(new Runnable()
        {

            @Override
            public void run()
            {
                EvECharacter selected = allCharactersList.getSelectedValue();
                if(selected != null)
                {
                    try
                    {
                        buttonPanel.setEnabled(false);
                        progressBar.setIndeterminate(true);
                        CharacterSheetDialog dialog = new CharacterSheetDialog(FurryIronman.this, selected);
                        dialog.setVisible(true);
                        

                    }
                    finally
                    {
                        buttonPanel.setEnabled(true);
                        progressBar.setIndeterminate(false);
                    }
                }
            }
        }, true
        ));
        
        buttonPanel = new ButtonPanel();
        buttonPanel.addButton(reloadAllCharactersButton);
        buttonPanel.addButton(statusButton);
        buttonPanel.addButton(characterInfoButton);
        
        
        DefaultListModel<EvECharacter> listModel = new DefaultListModel<>();
        allCharactersList = new JList<>(listModel);
        allCharactersList.setCellRenderer(new EveCharacterRenderer(controller));
        
        add(progressBar, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(allCharactersList), BorderLayout.CENTER);
        
        setMinimumSize(new Dimension(480, 320));
        validate();
        pack();
        setLocationRelativeTo(null);
        threadPool.execute(FurryIronman.this);
        return this;
    }
    
    
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    try
                    {
//                        Locale.setDefault(Locale.GERMAN);
//                        UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
//                        UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());
//                        UIManager.setLookAndFeel(new SubstanceTwilightLookAndFeel());
//                        UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
                        UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel());
                        
                        JFrame.setDefaultLookAndFeelDecorated(true);
                        JDialog.setDefaultLookAndFeelDecorated(true);
                    }
                    catch(UnsupportedLookAndFeelException ex){logger.warning("laf is not supported!");}
                    new FurryIronman().init().setVisible(true);
                }
                catch(PropertyNotFoundException ex)
                {
                    logger.severe("property missing. please check your configuration!");
                    ex.printStackTrace(System.err);
                }
                catch(ClassNotFoundException ex)
                {
                    logger.severe("unable to load dependency. missing jdbc driver?");
                    ex.printStackTrace(System.err);
                }
                catch(ParseException | IOException | SQLException | URISyntaxException | ParserConfigurationException | SAXException | ControllerException ex)
                {
                    logger.log(Level.SEVERE, "critical error: {0}", ex.getMessage());
                    ex.printStackTrace(System.err);
                }
            }
        });
    }

    @Override
    public void run()
    {
        try
        {
            buttonPanel.setEnabled(false);
            progressBar.setIndeterminate(true);
            progressBar.setString("revalidating api keys...");
            
            controller.revalidateAllApiKeys();
            logger.info("done revalidating all api keys.");
            progressBar.setString("done revalidating all api keys.");
            
            controller.reloadCharacterList();
            DefaultListModel<EvECharacter> model = (DefaultListModel<EvECharacter>) allCharactersList.getModel();
            model.removeAllElements();
            
            for(EvECharacter character : controller.getModel().getCharacters())
            {
                model.addElement(character);
                
            }            
            logger.info("done reloading all characters.");
            progressBar.setString("done reloading all characters.");
            
            controller.reloadSkillInTraining();
            logger.info("done reloading skills in training infos.");
            progressBar.setString("done reloading skills in training infos.");
            
            controller.reloadAllCharacterImages();
            logger.info("done reloading all characters images.");
            progressBar.setString("done reloading all characters images.");
            
            controller.reloadAllEveCorporationImages();
            logger.info("done reloading all corp images.");
            progressBar.setString("done reloading all corp images.");
                        
            threadPool.submit(renderThread);
            logger.info("render thread started...");
            
            
        }
        catch(ParseException | URISyntaxException | IOException | ParserConfigurationException | SAXException | ControllerException ex)
        {
            ex.printStackTrace(System.err);
            progressBar.setString("error");
        }
        finally
        {
            progressBar.setIndeterminate(false);
            buttonPanel.setEnabled(true);
        }
    }
    
    class RenderThread implements Runnable
    {
        private boolean runflag;
        
        public RenderThread()
        {
            runflag = true;
        }
        
        public void stop()
        {
            runflag = false;
        }
        
        @Override
        public void run()
        {
            try
            {
                while(!Thread.currentThread().isInterrupted() && runflag)
                {
                    allCharactersList.repaint();
                    Thread.sleep(1000l);
                }
            }
            catch(InterruptedException ex)
            {
                logger.severe("error executing render thread. thread got shut down...");
                ex.printStackTrace(System.err);
            }
        }        
    }
}
