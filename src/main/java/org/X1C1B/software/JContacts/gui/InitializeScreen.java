package org.X1C1B.software.JContacts.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.X1C1B.software.JContacts.gui.util.LanguageManager;
import org.X1C1B.software.JContacts.gui.util.Screen;
import org.X1C1B.software.JContacts.gui.util.SettingsManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class InitializeScreen implements Screen {
    private JTextField dbPathField;
    private JButton openBtn;
    private JLabel exceptionMsgLbl;
    private JPanel rootPanel;

    public InitializeScreen() {
        this.setupFormUI();
    }

    /**
     * @see Screen
     */

    @Override
    public JPanel getPanel() {
        return this.rootPanel;
    }

    public String getPropertyDatabasePath() {
        return this.dbPathField.getText();
    }

    private void setupFormUI() {
        this.dbPathField.setBorder(null);
        this.exceptionMsgLbl.setText(LanguageManager.getInstance().getValue("db_not_found_msg"));
        this.openBtn.addActionListener(new ChangeDatabasePathListener());
    }

    public void fillProperties() {
        try {
            this.dbPathField.setText(SettingsManager.getInstance().getProperty("db_path"));
        } catch (IOException exc) {
            throw new InternalError(exc);
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
        rootPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        dbPathField = new JTextField();
        dbPathField.setEditable(false);
        rootPanel.add(dbPathField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(400, -1), new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootPanel.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Database:");
        rootPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openBtn = new JButton();
        openBtn.setText("Open");
        rootPanel.add(openBtn, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exceptionMsgLbl = new JLabel();
        exceptionMsgLbl.setText("");
        rootPanel.add(exceptionMsgLbl, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    class ChangeDatabasePathListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Database Files", "db"));

            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                File selectedFile = fileChooser.getSelectedFile();
                dbPathField.setText(selectedFile.getAbsolutePath());
            }
        }
    }
}
