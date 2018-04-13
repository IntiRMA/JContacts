package org.tools.social;

import javax.swing.*;
import java.awt.*;

import org.tools.social.gui.SettingsScreen;
import org.tools.social.gui.event.*;
import org.tools.social.gui.MainScreen;
import org.tools.social.gui.EditScreen;
import org.tools.social.gui.AppendScreen;

import org.tools.social.gui.event.handler.MoveWindowListener;
import org.tools.social.gui.util.LanguageManager;
import org.tools.social.gui.util.SettingsManager;
import org.tools.social.util.Contact;
import org.tools.social.util.SqlContactDatabase;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

/**
 * The Application class implements the central class with
 * the entry method of this address book application. Moreover
 * this class holds the different screens and is responsible
 * for switching between them.
 *
 * @since 2018-03-22
 */

public class Application
{
    private JFrame frame;
    private MainScreen mainScreen;
    private AppendScreen appendScreen;
    private EditScreen editScreen;

    Application()
    {
        SettingsManager.initialize("Settings", new SettingsScreen());

        try
        {
            this.setupLanguage();
            this.setupDatabase();
        }
        catch(IOException exc)
        {
            throw new InternalError(exc);
        }

        this.setupMainScreenUI();
        this.setupAppendScreenUI();
        this.setupEditScreenUI();
        this.setupFrameUI();

        LanguageManager.getInstance().addListener(this.mainScreen);
        LanguageManager.getInstance().addListener(this.appendScreen);
        LanguageManager.getInstance().addListener(this.editScreen);
        LanguageManager.getInstance().updateLocale();
    }

    private void setupLanguage() throws IOException
    {
        Locale configLocale = new Locale(SettingsManager.getInstance().getProperty("language"));
        LanguageManager.getInstance().updateLocale(configLocale);
    }

    private void setupDatabase() throws IOException
    {
        Path configDatabasePath = Paths.get(SettingsManager.getInstance().getProperty("db_path"));
        String configDatabaseTable = SettingsManager.getInstance().getProperty("db_table");

        try
        {
            SqlContactDatabase.initialize(configDatabasePath.toString(), configDatabaseTable);
        }
        catch(FileNotFoundException exc)
        {
            JOptionPane.showMessageDialog(null,
                    LanguageManager.getInstance().getValue("db_not_found_msg"),
                    "Error", JOptionPane.ERROR_MESSAGE);

            throw new InternalError(LanguageManager.getInstance().getValue("db_not_found_msg"));
        }
        catch(ClassNotFoundException | SQLException exc)
        {
            JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
            throw new InternalError(exc);
        }
    }

    private void setupFrameUI()
    {
        this.frame = new JFrame();

        MoveWindowListener moveWindowListener = new MoveWindowListener(frame);
        frame.addMouseListener(moveWindowListener);
        frame.addMouseMotionListener(moveWindowListener);

        frame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override public void windowClosing(java.awt.event.WindowEvent event)
            {
                quitDialog();
            }
        });

        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setContentPane(this.mainScreen.getPanel());
        this.frame.setMinimumSize(new Dimension(920, 580));
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.pack();
    }

    private void setupMainScreenUI()
    {
        this.mainScreen = new MainScreen();

        this.mainScreen.addListener(new SwitchAppendScreenListener()
        {
            public void switchAppendScreenPerformed()
            {
                appendScreen.clearInputFields();
                frame.setContentPane(appendScreen.getPanel());
                frame.pack();
                frame.repaint();
            }
        });

        this.mainScreen.addListener(new SwitchEditScreenListener()
        {
            public void switchEditScreenPerformed(Contact contact)
            {
                editScreen.fillContactForm(contact);
                frame.setContentPane(editScreen.getPanel());
                frame.pack();
                frame.repaint();
            }
        });
    }

    private void setupAppendScreenUI()
    {
        this.appendScreen = new AppendScreen();

        this.appendScreen.addListener(new SwitchMainScreenListener()
        {
            public void switchMainScreenPerformed()
            {
                appendScreen.clearInputFields();
                frame.setContentPane(mainScreen.getPanel());
                frame.pack();
                frame.repaint();
            }
        });

        this.appendScreen.addListener(new AppendContactListener()
        {
            public void addContactActionPerformed(Contact contact)
            {
                try
                {
                    SqlContactDatabase.getInstance().writeContact(contact);
                    mainScreen.addContactToList(contact);
                }
                catch(FileNotFoundException | SQLException exc)
                {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupEditScreenUI()
    {
        this.editScreen = new EditScreen();

        this.editScreen.addListener(new SwitchMainScreenListener()
        {
            @Override public void switchMainScreenPerformed()
            {
                frame.setContentPane(mainScreen.getPanel());
                frame.pack();
                frame.repaint();
            }
        });

        this.editScreen.addListener(new UpdateContactListener()
        {
            @Override public void updateContactActionPerformed(Contact contact)
            {
                try
                {
                    SqlContactDatabase.getInstance().updateContact(contact);
                    mainScreen.replaceContactAtList(contact);
                }
                catch(FileNotFoundException | SQLException exc)
                {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void quitDialog()
    {
        int response = JOptionPane.showConfirmDialog(null,
                LanguageManager.getInstance().getValue("quit_application_msg"),
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(JOptionPane.YES_OPTION == response)
        {
            this.quit();
        }
    }

    public void quit()
    {
        try
        {
            SqlContactDatabase.getInstance().close();
        }
        catch(Exception exc)
        {
            throw new InternalError(exc);
        }

        this.frame.dispose();
    }

    /**
     * This is the entry method of this application. This method simply
     * creates a new object of the Application class.
     *
     * @param args Unused.
     */

    public static void main(String [] args) throws IOException
    {
        new Application();
    }
}
