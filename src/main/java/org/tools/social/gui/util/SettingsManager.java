package org.tools.social.gui.util;

import org.tools.social.Application;
import org.tools.social.gui.SettingsScreen;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

public class SettingsManager
{
    private static final String INSTALLATION_DIRECTORY;
    private static final String DEFAULT_DB_FILE;
    private static final String CONFIG_FILE;

    private static Path configPath;
    private static SettingsManager instance;

    private String popUpTitle;
    private SettingsScreen popUpScreen;

    static
    {
        DEFAULT_DB_FILE = "register.db";
        CONFIG_FILE = "config.properties";

        try
        {
            INSTALLATION_DIRECTORY = Paths.get(SettingsManager.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath()).getParent().toString();
        }
        catch(URISyntaxException exc)
        {
            throw new ExceptionInInitializerError(exc);
        }
    }

    private SettingsManager(String title, SettingsScreen screen)
    {
        this.popUpTitle = title;
        this.popUpScreen = screen;
    }

    public static boolean initialize(String title, SettingsScreen screen)
    {
        if(null == SettingsManager.instance)
        {
            SettingsManager.configPath = Paths.get(SettingsManager.INSTALLATION_DIRECTORY + "/" +
                    SettingsManager.CONFIG_FILE);

            if(false == Files.exists(configPath))
            {
                JOptionPane.showMessageDialog(null,
                        LanguageManager.getInstance().getValue("config_not_found_msg"),
                        "Error", JOptionPane.ERROR_MESSAGE);

                try(OutputStream output = new FileOutputStream(SettingsManager.configPath.toFile()))
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
        try(FileInputStream configFile = new FileInputStream(SettingsManager.configPath.toFile()))
        {
            Properties properties = new Properties();
            properties.load(configFile);

            return properties.getProperty(name);
        }
    }

    public void setProperty(String name, String value) throws IOException
    {
        Properties properties = new Properties();

        try(FileInputStream configFileIn = new FileInputStream(SettingsManager.configPath.toFile()))
        {
            properties.load(configFileIn);
        }

        try(OutputStream configFileOut = new FileOutputStream(SettingsManager.configPath.toFile()))
        {
            properties.setProperty(name, value);
            properties.store(configFileOut, null);
        }
    }

    public void showUI() throws IOException
    {
        this.popUpScreen.fillSettingsForm();

        int response = JOptionPane.showConfirmDialog(null, this.popUpScreen.getPanel(), this.popUpTitle,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if(JOptionPane.OK_OPTION == response)
        {
            this.setProperty("language", this.popUpScreen.getSelectedLanguage());
            this.setProperty("db_path", this.popUpScreen.getSelectedDatabasePath());

            LanguageManager.getInstance().updateLocale(new Locale(this.getProperty("language")));
        }
    }
}
