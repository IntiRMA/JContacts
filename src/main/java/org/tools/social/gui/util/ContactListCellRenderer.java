package org.tools.social.gui.util;

import org.tools.social.util.Contact;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The standard class for rendering contact cells in a JList with the help
 * of a JLabel.
 *
 * @since 2018-03-23
 * @see Contact
 */

public class ContactListCellRenderer extends JLabel implements ListCellRenderer
{
    private static final Color HIGHLIGHT_COLOR;

    static
    {
        HIGHLIGHT_COLOR = new Color(46, 81, 141);
    }

    public ContactListCellRenderer()
    {
        this.setOpaque(true);
    }

    @Override public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus)
    {
        Contact contact = (Contact) value;

        this.setText(contact.getSurname() + ", " + contact.getPrename());
        this.setBorder(new EmptyBorder(10,10,10,10));

        if(true == isSelected)
        {
            this.setBackground(HIGHLIGHT_COLOR);
            this.setForeground(Color.WHITE);
        }
        else
        {
            this.setBackground(list.getBackground());
            this.setForeground(Color.WHITE);
        }

        return this;
    }
}