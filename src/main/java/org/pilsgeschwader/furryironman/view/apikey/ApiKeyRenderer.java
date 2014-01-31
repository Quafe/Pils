package org.pilsgeschwader.furryironman.view.apikey;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;

/**
 *
 * @author boreas
 */
public class ApiKeyRenderer extends DefaultListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
        ApiKey key = (ApiKey) value;
        setText(String.valueOf(key.getKeyId()));
        return this;
    }
}
