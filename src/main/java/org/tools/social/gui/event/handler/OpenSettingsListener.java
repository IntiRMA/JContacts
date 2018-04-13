package org.tools.social.gui.event.handler;

import org.tools.social.gui.util.Screen;
import org.tools.social.gui.util.SettingsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OpenSettingsListener implements ActionListener
{
    @Override public void actionPerformed(ActionEvent event)
    {
        try
        {
            SettingsManager.getInstance().showUI();
        }
        catch(IOException exc)
        {
            throw new InternalError(exc);
        }
    }
}
