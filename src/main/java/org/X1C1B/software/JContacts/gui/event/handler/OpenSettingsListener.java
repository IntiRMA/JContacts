package org.X1C1B.software.JContacts.gui.event.handler;

import org.X1C1B.software.JContacts.gui.util.SettingsManager;

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
