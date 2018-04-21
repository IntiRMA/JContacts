package org.X1C1B.software.JContacts.gui.util;

import org.X1C1B.software.JContacts.gui.SettingsScreen;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Properties;

public class SettingsManager
{
    private static final String INSTALLATION_DIRECTORY;
    private static final String DEFAULT_DB_FILE;
    private static final String CONFIG_FILE;

    private static File configPath;
    private static SettingsManager instance;

    private String popUpTitle;
    private SettingsScreen settingsScreen;

    static
    {
        DEFAULT_DB_FILE = "register.db";
        CONFIG_FILE = "config.properties";

        try
        {
            INSTALLATION_DIRECTORY = new File(SettingsManager.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath()).getParent();
        }
        catch(URISyntaxException exc)
        {
            throw new ExceptionInInitializerError(exc);
        }
    }

    private SettingsManager(String title, SettingsScreen screen)
    {
        this.popUpTitle = title;
        this.settingsScreen = screen;
    }

    public static boolean initialize(String title, SettingsScreen screen)
    {
        if(null == SettingsManager.instance)
        {
            SettingsManager.configPath = new File(SettingsManager.INSTALLATION_DIRECTORY + "/" +
                    SettingsManager.CONFIG_FILE);

            if(false == configPath.exists())
            {
                JOptionPane.showMessageDialog(null,
                        LanguageManager.getInstance().getValue("config_not_found_msg"),
                        "Error", JOptionPane.ERROR_MESSAGE);

                try(OutputStream output = new FileOutputStream(SettingsManager.configPath))
                {
                    Properties properties = new Properties();

                    properties.setProperty("language", "en");
                    properties.setProperty("db_path", SettingsManager.INSTALLATION_DIRECTORY + "/" +
                            SettingsManager.DEFAULT_DB_FILE);
                    properties.setProperty("db_table", "contacts");

                    properties.store(output, null);
                }
                catch(IOException exc)
                {
                    throw new InternalError(exc);
                }
            }

            SettingsManager.instance = new SettingsManager(title, screen);

            return true;
        }

        return false;
    }

    public static SettingsManager getInstance()
    {
        return instance;
    }

    public String getProperty(String name) throws IOException
    {
        try(FileInputStream configFile = new FileInputStream(SettingsManager.configPath))
        {
            Properties properties = new Properties();
            properties.load(configFile);

            return properties.getProperty(name);
        }
    }

    public void setProperty(String name, String value) throws IOException
    {
        Properties properties = new Properties();

        try(FileInputStream configFileIn = new FileInputStream(SettingsManager.configPath))
        {
            properties.load(configFileIn);
        }

        try(OutputStream configFileOut = new FileOutputStream(SettingsManager.configPath))
        {
            properties.setProperty(name, value);
            properties.store(configFileOut, null);
        }
    }

    public void showUI() throws IOException
    {
        this.settingsScreen.fillProperties();

        int response = JOptionPane.showConfirmDialog(null, this.settingsScreen.getPanel(), this.popUpTitle,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if(JOptionPane.OK_OPTION == response)
        {
            this.setProperty("language", this.settingsScreen.getPropertyLanguage());
            this.setProperty("db_path", this.settingsScreen.getPropertyDatabasePath());

            LanguageManager.getInstance().updateLocale(new Locale(this.getProperty("language")));
        }
    }
}
