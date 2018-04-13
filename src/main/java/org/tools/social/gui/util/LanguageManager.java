package org.tools.social.gui.util;

import org.tools.social.gui.event.ChangeLanguageListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager
{
    private static LanguageManager instance;

    private Locale currentLocale;

    private java.util.List <ChangeLanguageListener> changeLanguageListeners = new ArrayList<>();

    static
    {
        instance = new LanguageManager();
    }

    private LanguageManager()
    {
        this.currentLocale = Locale.ENGLISH;
    }

    public static LanguageManager getInstance()
    {
        return instance;
    }

    /**
     * This method adds a new listener for the 'locale changed' event.
     *
     * @param listener The listener which should added.
     * @see ChangeLanguageListener
     */

    public void addListener(ChangeLanguageListener listener)
    {
        this.changeLanguageListeners.add(listener);
    }

    /**
     * This method emit a 'locale changed' event and inform all connected
     * listeners.
     */

    private void emitLocaleChangedAction(Locale locale)
    {
        for(ChangeLanguageListener listener : this.changeLanguageListeners)
        {
            listener.languageChanged(locale);
        }
    }
    public void updateLocale()
    {
        this.updateLocale(this.currentLocale);
    }

    public void updateLocale(Locale locale)
    {
        if(true == Locale.FRENCH.equals(locale))
        {
            UIManager.put("OptionPane.cancelButtonText", "Annuler");
            UIManager.put("OptionPane.noButtonText", "Non");
            UIManager.put("OptionPane.okButtonText", "D'accord");
            UIManager.put("OptionPane.yesButtonText", "Oui");
        }
        else if(true == Locale.GERMAN.equals(locale))
        {
            UIManager.put("OptionPane.cancelButtonText", "Abbrechen");
            UIManager.put("OptionPane.noButtonText", "Nein");
            UIManager.put("OptionPane.okButtonText", "OK");
            UIManager.put("OptionPane.yesButtonText", "Ja");
        }
        else if(true == Locale.ENGLISH.equals(locale))
        {
            UIManager.put("OptionPane.cancelButtonText", "Cancel");
            UIManager.put("OptionPane.noButtonText", "No");
            UIManager.put("OptionPane.okButtonText", "OK");
            UIManager.put("OptionPane.yesButtonText", "Yes");
        }

        this.currentLocale = locale;
        this.emitLocaleChangedAction(locale);
    }

    public String getValue(String key)
    {
        ResourceBundle bundle = ResourceBundle.getBundle("locale.JContacts", this.currentLocale);

        return bundle.getString(key);
    }
}
