package org.tools.social.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.tools.social.gui.event.handler.OpenSettingsListener;
import org.tools.social.gui.util.Screen;
import org.tools.social.gui.util.*;
import org.tools.social.gui.event.*;
import org.tools.social.util.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The MainScreen class implements the central screen
 * of this address book application which provides the
 * util of stored contacts and a detailed view of contact
 * depended information.
 *
 * @see Screen
 * @since 2018-03-22
 */

public class MainScreen implements Screen, ChangeLanguageListener {
    private JPanel rootPanel;
    private JList contactList;
    private JLabel contactIconLabel;
    private JLabel emailIconLabel;
    private JLabel phoneIconLabel;
    private JLabel homepageIconLabel;
    private JLabel locationIconLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel prenameLabel;
    private JLabel surnameLabel;
    private JLabel homepageLabel;
    private JLabel locationLabel;
    private JButton appendScreenBtn;
    private JButton currentScreenBtn;
    private JButton deleteContactBtn;
    private JPanel listPanel;
    private JScrollPane listScrollPane;
    private JPanel sideBarPanel;
    private JPanel headerPanel;
    private JPanel bodyPanel;
    private JButton editScreenBtn;
    private JButton exportContactBtn;
    private JLabel titleSloganLabel;
    private JButton infoBtn;
    private JButton socialGitHubBtn;

    private ContactListModel<Contact> listModel;
    private java.util.List<SwitchEditScreenListener> switchEditScreenListeners = new ArrayList<>();
    private java.util.List<SwitchAppendScreenListener> switchAppendScreenListeners = new ArrayList<>();

    /**
     * The constructor reads all stored contacts from database and add them
     * to the util. Moreover it initializes the complete user interface of
     * the central screen.
     *
     * @see SqlContactDatabase
     */

    public MainScreen() {
        this.setupSideBarUI();
        this.setupListUI();
        this.setupFormUI();

        try {
            Contact[] storedContacts = SqlContactDatabase.getInstance().readAllContacts();

            for (Contact contact : storedContacts) {
                this.addContactToList(contact);
            }
        } catch (SQLException exc) {
            JOptionPane.showMessageDialog(null, exc,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupSideBarUI() {
        this.currentScreenBtn.setBorderPainted(false);
        this.currentScreenBtn.setMargin(new Insets(0, 0, 0, 0));
        this.currentScreenBtn.setIcon(IconManager.ICON_MENU.getIcon());

        this.appendScreenBtn.setBorderPainted(false);
        this.appendScreenBtn.setMargin(new Insets(0, 0, 0, 0));
        this.appendScreenBtn.setIcon(IconManager.ICON_ADD_CONTACT.getIcon());
        this.appendScreenBtn.addActionListener(new SwitchAppendScreenActionListener());

        this.editScreenBtn.setBorderPainted(false);
        this.editScreenBtn.setMargin(new Insets(0, 0, 0, 0));
        this.editScreenBtn.setIcon(IconManager.ICON_EDIT_CONTACT.getIcon());
        this.editScreenBtn.addActionListener(new SwitchEditScreenActionListener());

        this.infoBtn.setBorderPainted(false);
        this.infoBtn.setMargin(new Insets(0, 0, 0, 0));
        this.infoBtn.setIcon(IconManager.ICON_SETTINGS.getIcon());
        this.infoBtn.addActionListener(new OpenSettingsListener());
    }

    private void setupListUI() {
        this.listModel = new ContactListModel<Contact>();
        this.listPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.listScrollPane.setBorder(null);

        this.contactList.setModel(this.listModel);
        this.contactList.setAutoscrolls(true);
        this.contactList.setCellRenderer(new ContactListCellRenderer());
        this.contactList.setFixedCellHeight(30);
        contactList.addListSelectionListener(new ListSelectionChangedListener());
    }

    private void setupFormUI() {
        this.contactIconLabel.setIcon(IconManager.ICON_CONTACT.getIcon());
        this.phoneIconLabel.setIcon(IconManager.ICON_PHONE.getIcon());
        this.emailIconLabel.setIcon(IconManager.ICON_EMAIL.getIcon());
        this.homepageIconLabel.setIcon(IconManager.ICON_HOMEPAGE.getIcon());
        this.locationIconLabel.setIcon(IconManager.ICON_LOCATION.getIcon());

        this.deleteContactBtn.setBorderPainted(false);
        this.deleteContactBtn.setMargin(new Insets(0, 0, 0, 0));
        this.deleteContactBtn.setIcon(IconManager.ICON_DELETE_CONTACT.getIcon());
        this.deleteContactBtn.addActionListener(new DeleteContactActionListener());

        this.exportContactBtn.setBorderPainted(false);
        this.exportContactBtn.setMargin(new Insets(0, 0, 0, 0));
        this.exportContactBtn.setIcon(IconManager.ICON_EXPORT_CONTACT.getIcon());
        this.exportContactBtn.addActionListener(new ExportContactActionListener());

        this.socialGitHubBtn.setBorderPainted(false);
        this.socialGitHubBtn.setMargin(new Insets(0, 0, 0, 0));
        this.socialGitHubBtn.setIcon(IconManager.ICON_GITHUB.getIcon());
        this.socialGitHubBtn.addActionListener(new BrowseGitHubRepositoryListener());
    }

    /**
     * @see Screen
     */

    @Override
    public JPanel getPanel() {
        return this.rootPanel;
    }

    /**
     * @see ChangeLanguageListener
     */

    @Override
    public void languageChanged(Locale locale) {
        this.titleSloganLabel.setText(LanguageManager.getInstance().getValue("title_screen_slogan"));

        this.currentScreenBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_current_screen_btn"));
        this.appendScreenBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_append_screen_btn"));
        this.editScreenBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_edit_screen_btn"));
        this.infoBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_info_btn"));

        this.deleteContactBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_delete_contact_btn"));
        this.exportContactBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_export_contact_btn"));
    }

    /**
     * This method adds a single contact temporarily to the internal contact util model. This
     * action is temporary and the stored data will lose after a restart without writing the
     * data to the database before.
     *
     * @param contact The contact which should added to util.
     * @see ContactListModel
     */

    public void addContactToList(Contact contact) {
        this.listModel.addElement(new Contact(contact));
    }

    /**
     * This method updates a single contact temporarily at the internal contact util model. This
     * action is temporary and the stored data will lose after a restart without writing the
     * data to the database before.
     *
     * @param contact The contact which should updated at util.
     * @see ContactListModel
     */

    public void replaceContactAtList(Contact contact) {
        for (int index = 0; index < this.listModel.getSize(); ++index) {
            Contact storedContact = (Contact) this.listModel.getElementAt(index);

            if (true == storedContact.getPrename().equals(contact.getPrename()) &&
                    true == storedContact.getSurname().equals((contact.getSurname()))) {
                this.listModel.removeElementAt(index);
                this.addContactToList(contact);
            }
        }
    }

    /**
     * This method adds a new listener for the 'switch to append screen' event.
     *
     * @param listener The listener which should added.
     * @see SwitchAppendScreenListener
     */

    public void addListener(SwitchAppendScreenListener listener) {
        this.switchAppendScreenListeners.add(listener);
    }

    /**
     * This method emit a 'switch to append screen' event and inform all connected
     * listeners.
     */

    private void emitSwitchAppendScreenAction() {
        for (SwitchAppendScreenListener listener : this.switchAppendScreenListeners) {
            listener.switchAppendScreenPerformed();
        }
    }

    /**
     * This method adds a new listener for the 'switch to edit screen' event.
     *
     * @param listener The listener which should added.
     * @see SwitchAppendScreenListener
     */

    public void addListener(SwitchEditScreenListener listener) {
        this.switchEditScreenListeners.add(listener);
    }

    /**
     * This method emit a 'switch to edit screen' event and inform all connected
     * listeners.
     */

    private void emitSwitchEditScreenAction(Contact contact) {
        for (SwitchEditScreenListener listener : this.switchEditScreenListeners) {
            listener.switchEditScreenPerformed(contact);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), 0, 0));
        sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), 0, 0));
        sideBarPanel.setBackground(new Color(-16772558));
        rootPanel.add(sideBarPanel, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(60, -1), null, 0, false));
        appendScreenBtn = new JButton();
        appendScreenBtn.setBackground(new Color(-16772558));
        appendScreenBtn.setText("");
        appendScreenBtn.setToolTipText("");
        sideBarPanel.add(appendScreenBtn, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        currentScreenBtn = new JButton();
        currentScreenBtn.setBackground(new Color(-13741683));
        currentScreenBtn.setText("");
        sideBarPanel.add(currentScreenBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        editScreenBtn = new JButton();
        editScreenBtn.setBackground(new Color(-16772558));
        editScreenBtn.setText("");
        editScreenBtn.setToolTipText("");
        sideBarPanel.add(editScreenBtn, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        infoBtn = new JButton();
        infoBtn.setBackground(new Color(-16772558));
        infoBtn.setText("");
        infoBtn.setToolTipText("");
        sideBarPanel.add(infoBtn, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        final Spacer spacer1 = new Spacer();
        sideBarPanel.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 0, 0));
        bodyPanel.setBackground(new Color(-12291125));
        rootPanel.add(bodyPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), 0, 0));
        listPanel.setBackground(new Color(-14667942));
        bodyPanel.add(listPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        listScrollPane = new JScrollPane();
        listPanel.add(listScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(200, -1), new Dimension(250, -1), null, 0, false));
        contactList = new JList();
        contactList.setBackground(new Color(-12291125));
        Font contactListFont = this.$$$getFont$$$(null, Font.BOLD, -1, contactList.getFont());
        if (contactListFont != null) contactList.setFont(contactListFont);
        contactList.setForeground(new Color(-1));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        contactList.setModel(defaultListModel1);
        contactList.setSelectionBackground(new Color(-13741683));
        contactList.setSelectionMode(0);
        listScrollPane.setViewportView(contactList);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(20, 20, 20, 20), 20, 20));
        panel1.setBackground(new Color(-12291125));
        panel1.setForeground(new Color(-1));
        bodyPanel.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-12291125));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(90, 80), null, 0, false));
        contactIconLabel = new JLabel();
        contactIconLabel.setText("");
        panel2.add(contactIconLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailLabel = new JLabel();
        Font emailLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, emailLabel.getFont());
        if (emailLabelFont != null) emailLabel.setFont(emailLabelFont);
        emailLabel.setForeground(new Color(-1));
        emailLabel.setText("-");
        panel2.add(emailLabel, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prenameLabel = new JLabel();
        Font prenameLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, prenameLabel.getFont());
        if (prenameLabelFont != null) prenameLabel.setFont(prenameLabelFont);
        prenameLabel.setForeground(new Color(-1));
        prenameLabel.setText("-");
        panel2.add(prenameLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailIconLabel = new JLabel();
        emailIconLabel.setText("");
        panel2.add(emailIconLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneIconLabel = new JLabel();
        phoneIconLabel.setText("");
        panel2.add(phoneIconLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneLabel = new JLabel();
        Font phoneLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, phoneLabel.getFont());
        if (phoneLabelFont != null) phoneLabel.setFont(phoneLabelFont);
        phoneLabel.setForeground(new Color(-1));
        phoneLabel.setText("-");
        panel2.add(phoneLabel, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        homepageIconLabel = new JLabel();
        homepageIconLabel.setText("");
        panel2.add(homepageIconLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationIconLabel = new JLabel();
        locationIconLabel.setText("");
        panel2.add(locationIconLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        homepageLabel = new JLabel();
        Font homepageLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, homepageLabel.getFont());
        if (homepageLabelFont != null) homepageLabel.setFont(homepageLabelFont);
        homepageLabel.setForeground(new Color(-1));
        homepageLabel.setText("-");
        panel2.add(homepageLabel, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationLabel = new JLabel();
        Font locationLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, locationLabel.getFont());
        if (locationLabelFont != null) locationLabel.setFont(locationLabelFont);
        locationLabel.setForeground(new Color(-1));
        locationLabel.setText("-");
        panel2.add(locationLabel, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        surnameLabel = new JLabel();
        Font surnameLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, surnameLabel.getFont());
        if (surnameLabelFont != null) surnameLabel.setFont(surnameLabelFont);
        surnameLabel.setForeground(new Color(-1));
        surnameLabel.setText("-");
        panel2.add(surnameLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(5, 0, 0, 0), 0, 5));
        panel3.setBackground(new Color(-12291125));
        bodyPanel.add(panel3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deleteContactBtn = new JButton();
        deleteContactBtn.setBackground(new Color(-13741683));
        deleteContactBtn.setText("");
        deleteContactBtn.setToolTipText("");
        panel3.add(deleteContactBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exportContactBtn = new JButton();
        exportContactBtn.setBackground(new Color(-13741683));
        exportContactBtn.setText("");
        exportContactBtn.setToolTipText("");
        panel3.add(exportContactBtn, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));
        rootPanel.add(headerPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 175), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 20, 0, 0), 0, 0));
        headerPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 36, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("JContacts");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleSloganLabel = new JLabel();
        titleSloganLabel.setText("Easy management of contacts");
        panel4.add(titleSloganLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(5, 0, 0, 0), -1, -1));
        headerPanel.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        socialGitHubBtn = new JButton();
        socialGitHubBtn.setBackground(new Color(-4539718));
        socialGitHubBtn.setText("");
        socialGitHubBtn.setToolTipText("GitHub");
        panel5.add(socialGitHubBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    private class ListSelectionChangedListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            JList list = (JList) event.getSource();
            int index = list.getSelectedIndex();
            final String EMPTY_FIELD = "-";

            if (index >= list.getModel().getSize() || 0 > index) {
                prenameLabel.setText(EMPTY_FIELD);
                surnameLabel.setText(EMPTY_FIELD);
                phoneLabel.setText(EMPTY_FIELD);
                emailLabel.setText(EMPTY_FIELD);
                homepageLabel.setText(EMPTY_FIELD);
                locationLabel.setText(EMPTY_FIELD);
            } else {
                Contact contact = (Contact) list.getModel().getElementAt(index);

                prenameLabel.setText(contact.getPrename());
                surnameLabel.setText(contact.getSurname());
                phoneLabel.setText(contact.getPhoneNumber());
                emailLabel.setText(contact.getEmailAddress());
                homepageLabel.setText(contact.getHomepage());
                locationLabel.setText(contact.getLocation());
            }
        }
    }

    private class ExportContactActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int index = contactList.getSelectedIndex();

            if (index >= 0) {
                Contact contact = (Contact) listModel.getElementAt(index);

                try {
                    XmlContactExport.exportContact(contact.getSurname() + "_" +
                            contact.getPrename() + ".xml", contact);

                    JOptionPane.showMessageDialog(null,
                            LanguageManager.getInstance().getValue("contact_exported_msg"),
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (TransformerException | ParserConfigurationException exc) {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        LanguageManager.getInstance().getValue("no_contact_selected_msg"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class SwitchAppendScreenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            emitSwitchAppendScreenAction();
        }
    }

    private class SwitchEditScreenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int index = contactList.getSelectedIndex();

            if (index >= 0) {
                emitSwitchEditScreenAction((Contact) listModel.getElementAt(index));
            } else {
                JOptionPane.showMessageDialog(null,
                        LanguageManager.getInstance().getValue("no_contact_selected_msg"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class DeleteContactActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int index = contactList.getSelectedIndex();

            if (index >= 0) {
                Contact contact = (Contact) listModel.getElementAt(index);

                try {
                    SqlContactDatabase.getInstance().deleteContact(contact);
                    listModel.removeElementAt(index);
                } catch (FileNotFoundException | SQLException exc) {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        LanguageManager.getInstance().getValue("no_contact_selected_msg"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class BrowseGitHubRepositoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/0x1C1B/JContacts"));
                } catch (IOException | URISyntaxException exc) {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        LanguageManager.getInstance().getValue("no_browser_found_msg"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }

        }
    }
}
