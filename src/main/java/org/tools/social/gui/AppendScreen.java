package org.tools.social.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.PlainDocument;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.tools.social.gui.event.*;
import org.tools.social.gui.event.handler.OpenSettingsListener;
import org.tools.social.gui.util.Screen;
import org.tools.social.gui.util.*;
import org.tools.social.util.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The AppendScreen class implements a special screen to add
 * new contacts to the existing util of this address book application.
 * The screen provides a form to enter information about a contact and
 * an option to add this new contact with it's filled information to the util.
 *
 * @see Screen
 * @since 2018-03-22
 */

public class AppendScreen implements Screen, ChangeLanguageListener {
    private JPanel rootPanel;
    private JButton cancelBtn;
    private JButton applyContactBtn;
    private JTextField prenameField;
    private JTextField surnameField;
    private JLabel contactIcon;
    private JTextField phoneField;
    private JLabel emailIcon;
    private JTextField emailField;
    private JLabel phoneIcon;
    private JTextField homepageField;
    private JLabel homepageIcon;
    private JLabel locationIcon;
    private JComboBox locationField;
    private JButton currentScreenBtn;
    private JButton mainScreenBtn;
    private JButton appendContactBtn;
    private JPanel sideBarPanel;
    private JPanel bodyPanel;
    private JPanel footerPanel;
    private JButton editScreenBtn;
    private JButton importContactBtn;
    private JButton infoBtn;

    private java.util.List<SwitchMainScreenListener> switchMainScreenListeners = new ArrayList<>();
    private java.util.List<AppendContactListener> addContactListeners = new ArrayList<>();

    /**
     * The constructor initializes the complete user interface of
     * this screen.
     */

    public AppendScreen() {
        this.setupSideBarUI();
        this.setupContactFormUI();
    }

    private void setupSideBarUI() {
        this.mainScreenBtn.setBorderPainted(false);
        this.mainScreenBtn.setMargin(new Insets(0, 0, 0, 0));
        this.mainScreenBtn.setIcon(IconManager.ICON_MENU.getIcon());
        this.mainScreenBtn.addActionListener(new OnButtonMainScreenClicked());

        this.currentScreenBtn.setBorderPainted(false);
        this.currentScreenBtn.setMargin(new Insets(0, 0, 0, 0));
        this.currentScreenBtn.setIcon(IconManager.ICON_ADD_CONTACT.getIcon());

        this.editScreenBtn.setBorderPainted(false);
        this.editScreenBtn.setMargin(new Insets(0, 0, 0, 0));
        this.editScreenBtn.setIcon(IconManager.ICON_EDIT_CONTACT.getIcon());

        this.infoBtn.setBorderPainted(false);
        this.infoBtn.setMargin(new Insets(0, 0, 0, 0));
        this.infoBtn.setIcon(IconManager.ICON_SETTINGS.getIcon());
        this.infoBtn.addActionListener(new OpenSettingsListener());
    }

    private void setupContactFormUI() {
        this.contactIcon.setIcon(IconManager.ICON_CONTACT.getIcon());
        this.emailIcon.setIcon(IconManager.ICON_EMAIL.getIcon());
        this.phoneIcon.setIcon(IconManager.ICON_PHONE.getIcon());
        this.homepageIcon.setIcon(IconManager.ICON_HOMEPAGE.getIcon());
        this.locationIcon.setIcon(IconManager.ICON_LOCATION.getIcon());

        this.prenameField.setBorder(null);
        this.prenameField.setDocument(new TextFieldLimit(20));

        this.surnameField.setBorder(null);
        this.surnameField.setDocument(new TextFieldLimit(20));

        this.emailField.setBorder(null);
        this.emailField.setDocument(new TextFieldLimit(40));

        this.phoneField.setBorder(null);
        PlainDocument document = (PlainDocument) this.phoneField.getDocument();
        document.setDocumentFilter(new DigitLimitFilter(20, '/', '+', ' '));

        this.homepageField.setBorder(null);
        this.homepageField.setDocument(new TextFieldLimit(40));

        this.appendContactBtn.setBorderPainted(false);
        this.appendContactBtn.setMargin(new Insets(0, 0, 0, 0));
        this.appendContactBtn.setIcon(IconManager.ICON_APPLY_CONTACT.getIcon());
        this.appendContactBtn.addActionListener(new OnButtonAppendContactClicked());

        this.importContactBtn.setBorderPainted(false);
        this.importContactBtn.setMargin(new Insets(0, 0, 0, 0));
        this.importContactBtn.setIcon(IconManager.ICON_IMPORT_CONTACT.getIcon());
        this.importContactBtn.addActionListener(new OnButtonImportContactClicked());
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
        this.currentScreenBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_current_screen_btn"));
        this.mainScreenBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_main_screen_btn"));
        this.editScreenBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_edit_screen_btn"));
        this.infoBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_info_btn"));

        this.prenameField.setToolTipText(LanguageManager.getInstance().getValue("tooltip_prename_field"));
        this.surnameField.setToolTipText(LanguageManager.getInstance().getValue("tooltip_surname_field"));
        this.emailField.setToolTipText(LanguageManager.getInstance().getValue("tooltip_email_field"));
        this.phoneField.setToolTipText(LanguageManager.getInstance().getValue("tooltip_phone_field"));
        this.homepageField.setToolTipText(LanguageManager.getInstance().getValue("tooltip_homepage_field"));
        this.locationField.setToolTipText(LanguageManager.getInstance().getValue("tooltip_location_field"));

        this.appendContactBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_add_contact_btn"));
        this.importContactBtn.setToolTipText(LanguageManager.getInstance().getValue("tooltip_import_contact_btn"));
    }

    /**
     * The purpose of this method is to clear all filled input forms of
     * this screen in the case of a switch of screens for example.
     */

    public void clearInputFields() {
        this.prenameField.setText("");
        this.surnameField.setText("");
        this.emailField.setText("");
        this.phoneField.setText("");
        this.homepageField.setText("");
        this.locationField.setSelectedIndex(0);
    }

    /**
     * This method adds a new listener for the 'switch to main screen' event.
     *
     * @param listener The listener which should added.
     * @see SwitchMainScreenListener
     */

    public void addListener(SwitchMainScreenListener listener) {
        this.switchMainScreenListeners.add(listener);
    }

    /**
     * This method emit a 'switch to main screen' event and inform all connected
     * listeners.
     */

    private void emitSwitchMainScreenAction() {
        for (SwitchMainScreenListener listener : this.switchMainScreenListeners) {
            listener.switchMainScreenPerformed();
            ;
        }
    }

    /**
     * This method adds a new listener for the 'add contact' event.
     *
     * @param listener The listener which should added.
     * @see AppendContactListener
     */

    public void addListener(AppendContactListener listener) {
        this.addContactListeners.add(listener);
    }

    /**
     * This method emit an 'add contact' event and inform all connected
     * listeners.
     */

    private void emitAddContactAction(Contact contact) {
        for (AppendContactListener listener : this.addContactListeners) {
            listener.addContactActionPerformed(contact);
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
        rootPanel.setBackground(new Color(-14667942));
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayoutManager(2, 1, new Insets(30, 10, 10, 10), 0, 10));
        bodyPanel.setBackground(new Color(-12291125));
        rootPanel.add(bodyPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), 5, 30));
        panel1.setBackground(new Color(-12291125));
        bodyPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        emailIcon = new JLabel();
        emailIcon.setText("");
        panel1.add(emailIcon, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneIcon = new JLabel();
        phoneIcon.setText("");
        panel1.add(phoneIcon, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        homepageIcon = new JLabel();
        homepageIcon.setText("");
        panel1.add(homepageIcon, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationIcon = new JLabel();
        locationIcon.setText("");
        panel1.add(locationIcon, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationField = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Select");
        defaultComboBoxModel1.addElement("Germany");
        defaultComboBoxModel1.addElement("United States");
        defaultComboBoxModel1.addElement("France");
        defaultComboBoxModel1.addElement("Great Britain");
        defaultComboBoxModel1.addElement("Russia");
        defaultComboBoxModel1.addElement("Italy");
        defaultComboBoxModel1.addElement("Spain");
        defaultComboBoxModel1.addElement("Canada");
        defaultComboBoxModel1.addElement("China");
        defaultComboBoxModel1.addElement("Japan");
        defaultComboBoxModel1.addElement("Austria");
        defaultComboBoxModel1.addElement("Switzerland");
        defaultComboBoxModel1.addElement("Poland");
        locationField.setModel(defaultComboBoxModel1);
        locationField.setToolTipText("");
        panel1.add(locationField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel2.setBackground(new Color(-12291125));
        panel1.add(panel2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        emailField = new JTextField();
        emailField.setBackground(new Color(-12291125));
        emailField.setForeground(new Color(-1));
        emailField.setToolTipText("");
        panel2.add(emailField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        separator1.setBackground(new Color(-1));
        panel2.add(separator1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 2), new Dimension(-1, 2), 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel3.setBackground(new Color(-12291125));
        panel1.add(panel3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        separator2.setBackground(new Color(-1));
        panel3.add(separator2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 2), new Dimension(-1, 2), 0, false));
        phoneField = new JTextField();
        phoneField.setBackground(new Color(-12291125));
        phoneField.setForeground(new Color(-1));
        phoneField.setText("");
        phoneField.setToolTipText("");
        panel3.add(phoneField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel4.setBackground(new Color(-12291125));
        panel1.add(panel4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        homepageField = new JTextField();
        homepageField.setBackground(new Color(-12291125));
        homepageField.setForeground(new Color(-1));
        homepageField.setToolTipText("");
        panel4.add(homepageField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JSeparator separator3 = new JSeparator();
        separator3.setBackground(new Color(-1));
        panel4.add(separator3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 2), new Dimension(-1, 2), 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), 20, 0));
        panel5.setBackground(new Color(-12291125));
        panel1.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JSeparator separator4 = new JSeparator();
        separator4.setBackground(new Color(-1));
        panel5.add(separator4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 2), new Dimension(-1, 2), 0, false));
        prenameField = new JTextField();
        prenameField.setBackground(new Color(-12291125));
        prenameField.setForeground(new Color(-1));
        prenameField.setText("");
        prenameField.setToolTipText("");
        panel5.add(prenameField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        surnameField = new JTextField();
        surnameField.setBackground(new Color(-12291125));
        surnameField.setForeground(new Color(-1));
        surnameField.setToolTipText("");
        panel5.add(surnameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JSeparator separator5 = new JSeparator();
        separator5.setBackground(new Color(-1));
        panel5.add(separator5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 2), new Dimension(-1, 2), 0, false));
        contactIcon = new JLabel();
        contactIcon.setText("");
        panel1.add(contactIcon, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        bodyPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), 0, 0));
        sideBarPanel.setBackground(new Color(-16772558));
        rootPanel.add(sideBarPanel, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(60, -1), null, 0, false));
        currentScreenBtn = new JButton();
        currentScreenBtn.setBackground(new Color(-13741683));
        currentScreenBtn.setText("");
        sideBarPanel.add(currentScreenBtn, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        mainScreenBtn = new JButton();
        mainScreenBtn.setBackground(new Color(-16772558));
        mainScreenBtn.setText("");
        sideBarPanel.add(mainScreenBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        editScreenBtn = new JButton();
        editScreenBtn.setBackground(new Color(-16772558));
        editScreenBtn.setEnabled(false);
        editScreenBtn.setText("");
        editScreenBtn.setToolTipText("");
        sideBarPanel.add(editScreenBtn, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        infoBtn = new JButton();
        infoBtn.setBackground(new Color(-16772558));
        infoBtn.setText("");
        infoBtn.setToolTipText("");
        sideBarPanel.add(infoBtn, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 50), null, 0, false));
        final Spacer spacer2 = new Spacer();
        sideBarPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        footerPanel = new JPanel();
        footerPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 10, 0, 10), -1, -1));
        footerPanel.setBackground(new Color(-12566464));
        rootPanel.add(footerPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        appendContactBtn = new JButton();
        appendContactBtn.setBackground(new Color(-12566464));
        appendContactBtn.setText("");
        appendContactBtn.setToolTipText("");
        footerPanel.add(appendContactBtn, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        footerPanel.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        importContactBtn = new JButton();
        importContactBtn.setBackground(new Color(-12566464));
        importContactBtn.setText("");
        importContactBtn.setToolTipText("");
        footerPanel.add(importContactBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    private class OnButtonMainScreenClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            emitSwitchMainScreenAction();
        }
    }

    private class OnButtonImportContactClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));

            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    Contact contact = XmlContactImport.importContact(selectedFile.getAbsolutePath());

                    JOptionPane.showMessageDialog(null,
                            LanguageManager.getInstance().getValue("contact_imported_msg"),
                            "Information", JOptionPane.INFORMATION_MESSAGE);

                    emitAddContactAction(contact);
                    clearInputFields();
                } catch (ParserConfigurationException | IOException | SAXException exc) {
                    JOptionPane.showMessageDialog(null, exc,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class OnButtonAppendContactClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String prename = prenameField.getText();
            String surname = surnameField.getText();
            String emailAddress = emailField.getText();
            String phoneNumber = phoneField.getText();
            String homepage = homepageField.getText();
            String location = (String) locationField.getSelectedItem();

            if (true == prename.isEmpty() ||
                    true == surname.isEmpty() ||
                    true == emailAddress.isEmpty() ||
                    true == phoneNumber.isEmpty() ||
                    true == homepage.isEmpty() ||
                    true == location.equals("Select")) {
                JOptionPane.showMessageDialog(null, "Not every field was filled!",
                        "Invalid input", JOptionPane.WARNING_MESSAGE);
            } else {
                if (false == new EmailValidator().validate(emailAddress)) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid email address format!",
                            "Invalid input", JOptionPane.WARNING_MESSAGE);
                } else {
                    Contact contact = new Contact(prename, surname, emailAddress,
                            phoneNumber, homepage, location);

                    emitAddContactAction(contact);
                    clearInputFields();

                    JOptionPane.showMessageDialog(null,
                            "The contact was added to database!",
                            "Contact added", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
