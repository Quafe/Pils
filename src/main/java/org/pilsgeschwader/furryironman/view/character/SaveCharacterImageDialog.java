package org.pilsgeschwader.furryironman.view.character;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.pilsgeschwader.furryironman.controller.common.ControllerException;
import org.pilsgeschwader.furryironman.model.eve.EvECharacter;
import org.pilsgeschwader.furryironman.view.FurryIronman;
import org.pilsgeschwader.furryironman.view.common.AbstractDialog;
import org.pilsgeschwader.furryironman.view.common.SimpleForm;

/**
 *
 * @author binary gamura
 */
public class SaveCharacterImageDialog extends AbstractDialog implements ActionListener, Runnable
{

    private JComboBox<Integer> imageSizeSelect;
    
    private JComboBox<EvECharacter> characterList;
    
    private final FurryIronman parentGUI;
    
    public SaveCharacterImageDialog(FurryIronman parentGUI)
    {
        super("save char portrait", parentGUI);
        this.parentGUI = parentGUI;
        init();
    }
    
    private void init()
    {
        imageSizeSelect = new JComboBox<>(new Integer[]{30, 32, 64, 128, 200, 256, 512, 1024});
        
        List<EvECharacter> chars = parentGUI.getController().getModel().getCharacters();
        characterList = new JComboBox<>(chars.toArray(new EvECharacter[chars.size()]));
        characterList.setRenderer(new CharacterListRenderer());
        
        SimpleForm form = new SimpleForm();        
        form.addRow("character:", characterList);
        form.addRow("size:", imageSizeSelect);
        
        JButton loadImageButton = new JButton("save");
        loadImageButton.addActionListener(this);
        
        add(form, BorderLayout.CENTER);
        
        buttonPanel.addButton(loadImageButton);
        addDefaultCloseButton();        
        
        validate();
        pack();
        setLocationRelativeTo(parentGUI);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        parentGUI.threadPool.submit(this);
    }

    @Override
    public void run()
    {
        try
        {
            startProgressBar("fetching char image from server...");
            int width = ((Number) imageSizeSelect.getSelectedItem()).intValue();
            EvECharacter character = (EvECharacter) characterList.getSelectedItem();
            Image image = parentGUI.getController().characterImageController.fetchImageFor(character.getCharacterID(), width, true);
            if(image != null)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("select directory");
                int result = chooser.showSaveDialog(SaveCharacterImageDialog.this);
                
                if(result == JFileChooser.APPROVE_OPTION)
                {
                    File partnetDir = chooser.getSelectedFile();
                    if(partnetDir != null)
                    { 
                        File file = new File(partnetDir, character.getCharacterName()+"("+width+"x"+width+").png");
                        ImageIO.write((BufferedImage) image, "png", file);
                        stopProgressBar("written image to "+file.getAbsolutePath()+".");
                    }
                    else
                    {
                        stopProgressBar("action canceled by user.");
                    }
                }
                else
                {
                    stopProgressBar("action canceled by user.");
                }
            }
        }
        catch(IOException | ControllerException ex)
        {
            showError("error", ex.getMessage());
            ex.printStackTrace(System.err);
        }
        finally
        {
            stopProgressBar(null);
        }
    }
    
    static class CharacterListRenderer extends DefaultListCellRenderer 
    {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            EvECharacter character = (EvECharacter) value;
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
            setText(character.getCharacterName());
            return this;
        }
        
       
        
    }
}
