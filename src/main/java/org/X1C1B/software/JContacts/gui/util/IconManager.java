package org.X1C1B.software.JContacts.gui.util;

import javax.swing.*;
import java.awt.*;

/**
 * This enum defines constants for all used icons, loading the required
 * image files from disk.
 *
 * @since 2018-03-22
 */

public enum IconManager
{
    ICON_ADD_CONTACT("/icon/add.png", 24, 24),
    ICON_DELETE_CONTACT("/icon/delete.png", 24, 24),
    ICON_APPLY_CONTACT("/icon/checkmark.png", 24, 24),
    ICON_EDIT_CONTACT("/icon/edit.png", 24, 24),
    ICON_CONTACT("/icon/contact.png", 24, 24),
    ICON_PHONE("/icon/phone.png", 24, 24),
    ICON_EMAIL("/icon/email.png", 24, 24),
    ICON_HOMEPAGE("/icon/homepage.png", 24, 24),
    ICON_LOCATION("/icon/location.png", 24, 24),
    ICON_MENU("/icon/menu.png", 24, 24),
    ICON_EXPORT_CONTACT("/icon/export.png", 24, 24),
    ICON_IMPORT_CONTACT("/icon/import.png", 24, 24),
    ICON_SETTINGS("/icon/settings.png", 24, 24),
    ICON_GITHUB("/icon/github.png", 24, 24);

    protected ImageIcon icon;

    IconManager(String path, int width, int height)
    {
        ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(path));

        Image image = imgIcon.getImage().getScaledInstance(width, height,
                Image.SCALE_SMOOTH);

        this.icon = new ImageIcon(image);
    }

    public ImageIcon getIcon()
    {
        return this.icon;
    }
}
