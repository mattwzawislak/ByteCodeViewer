package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.settings.Settings;
import org.obicere.bcviewer.settings.target.StringSetting;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 */
public class StringSettingPanel extends SettingPanel<String> {

    private final JLabel     descriptor;
    private final JTextField valueField;

    public StringSettingPanel(final Domain domain, final StringSetting setting) {
        super(setting);

        final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);

        this.descriptor = new JLabel(setting.getDescriptor());
        this.valueField = new JTextField(setting.getValue());

        valueField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                update();
            }

            private void update() {

                final Settings settings = domain.getSettingsController().getSettings();
                final String newValue = valueField.getText();
                final String name = setting.getName();

                settings.set(name, newValue);
            }
        });

        add(descriptor);
        add(Box.createHorizontalGlue());
        add(valueField);
    }

    public JLabel getDescriptor() {
        return descriptor;
    }

    public JTextField getValueField() {
        return valueField;
    }

    @Override
    public void setValue(final Object value) {
        setting.setValue(value);

        valueField.setText(value.toString());
    }
}
