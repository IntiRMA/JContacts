package org.tools.social.gui.util;

import javax.swing.JPanel;

/**
 * The Screen interface defines a general interface for all
 * screens with the central methods 'getPanel', which enables
 * external classes to access the root panel of each screen.
 *
 * @since 2018-03-22
 */

public interface Screen
{
    public abstract JPanel getPanel();
}
