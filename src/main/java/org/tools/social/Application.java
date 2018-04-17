package org.tools.social;

import javax.swing.*;
import java.awt.*;

import org.tools.social.gui.*;
import org.tools.social.gui.event.*;

import org.tools.social.gui.event.handler.MoveWindowListener;
import org.tools.social.gui.util.LanguageManager;
import org.tools.social.gui.util.SettingsManager;
import org.tools.social.util.Contact;
import org.tools.social.util.SqlContactDatabase;

import java.io.*;
import java.net.URI;
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

public class Application extends JFrame
{
    private MainScreen mainScreen;
    private AppendScreen appendScreen;
    private EditScreen editScreen;

    public static final Dimension DEFAULT_SIZE;

    static
    {
        DEFAULT_SIZE = new Dimension(920, 580);
    }

    public Application()
    {
        try
        {
            MoveWindowListener moveWindowListener = new MoveWindowListener(this);
            this.addMouseListener(moveWindowListener);
            this.addMouseMotionListener(moveWindowListener);

            this.addWindowListener(new java.awt.event.WindowAdapter()
            {
                @Override public void windowClosing(java.awt.event.WindowEvent event)
                {
                    Application.this.confirmQuitDialog();
                }
            });

            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.setMinimumSize(Application.DEFAULT_SIZE);
            this.setResizable(false);

            this.initPreferences();
            this.initDatabase();

            this.mainScreen = new MainScreen();
            this.appendScreen = new AppendScreen();
            this.editScreen = new EditScreen();

            this.initMainScreen();
            this.initAppendScreen();
            this.initEditScreen();

            this.initLanguage();

            this.setContentPane(this.mainScreen.getPanel());
            this.setVisible(true);
        }
        catch(Exception exc)
        {
            JOptionPane.showMessageDialog(null, exc,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void initPreferences() throws IOException
    {
        SettingsManager.initialize("Settings", new SettingsScreen());

        Locale configLocale = new Locale(SettingsManager.getInstance().getProperty("language"));
        LanguageManager.getInstance().updateLocale(configLocale);
    }

    private void initLanguage()
    {
        LanguageManager.getInstance().addListener(this.mainScreen);
        LanguageManager.getInstance().addListener(this.appendScreen);
        LanguageManager.getInstance().addListener(this.editScreen);

        LanguageManager.getInstance().refreshLocale();
    }

    private void initDatabase() throws IOException, ClassNotFoundException, SQLException
    {
        File file = new File(SettingsManager.getInstance().getProperty("db_path"));

        if(!file.exists() || file.isDirectory())
        {
            if(JOptionPane.OK_OPTION != this.setDatabasePathDialog())
            {
                throw new IOException(LanguageManager.getInstance().getValue("db_not_found_msg"));
            }
        }

        File databaseFile = new File(SettingsManager.getInstance().getProperty("db_path"));
        String databaseTable = SettingsManager.getInstance().getProperty("db_table");

        SqlContactDatabase.initialize(databaseFile, databaseTable);
    }

    private int setDatabasePathDialog() throws IOException
    {
        InitializeScreen initializeScreen = new InitializeScreen();
        initializeScreen.fillProperties();

        int response = JOptionPane.showConfirmDialog(null, initializeScreen.getPanel(),
                "Initialize", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if(JOptionPane.OK_OPTION == response)
        {
            SettingsManager.getInstance().setProperty("db_path", initializeScreen.getPropertyDatabasePath());
        }

        return response;
    }

    private void initMainScreen()
    {
        this.mainScreen.addListener(new SwitchAppendScreenListener()
        {
            public void switchAppendScreenPerformed()
            {
                Application.this.appendScreen.clearInputFields();

                Application.this.setContentPane(Application.this.appendScreen.getPanel());
                Application.this.pack();
                Application.this.repaint();
            }
        });

        this.mainScreen.addListener(new SwitchEditScreenListener()
        {
            public void switchEditScreenPerformed(Contact contact)
            {
                Application.this.editScreen.fillContactForm(contact);

                Application.this.setContentPane(Application.this.editScreen.getPanel());
                Application.this.pack();
                Application.this.repaint();
            }
        });
    }

    private void initAppendScreen()
    {
        this.appendScreen.addListener(new SwitchMainScreenListener()
        {
            public void switchMainScreenPerformed()
            {
                Application.this.appendScreen.clearInputFields();

                Application.this.setContentPane(mainScreen.getPanel());
                Application.this.pack();
                Application.this.repaint();
            }
        });

        this.appendScreen.addListener(new AppendContactListener()
        {
            public void addContactActionPerformed(Contact contact)
            {
                try
                {
                    SqlContactDatabase.getInstance().writeContact(contact);
                    Application.this.mainScreen.addContactToList(contact);
                }
                catch(FileNotFoundException | SQLException exc)
                {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void initEditScreen()
    {
        this.editScreen.addListener(new SwitchMainScreenListener()
        {
            @Override public void switchMainScreenPerformed()
            {
                Application.this.setContentPane(mainScreen.getPanel());
                Application.this.pack();
                Application.this.repaint();
            }
        });

        this.editScreen.addListener(new UpdateContactListener()
        {
            @Override public void updateContactActionPerformed(Contact contact)
            {
                try
                {
                    SqlContactDatabase.getInstance().updateContact(contact);
                    Application.this.mainScreen.replaceContactAtList(contact);
                }
                catch(FileNotFoundException | SQLException exc)
                {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void confirmQuitDialog()
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

        this.dispose();
        System.exit(0);
    }

    /**
     * This is the entry method of this application. This method simply
     * creates a new object of the Application class.
     *
     * @param args Unused.
     */

    public static void main(String [] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override public void run()
            {
                new Application();
            }
        });
    }
}
