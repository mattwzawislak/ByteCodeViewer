package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.settings.target.Setting;
import org.obicere.bcviewer.util.FontUtils;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;

/**
 */
public class FontSettingPanel extends SettingPanel<Font> {

    private final JSpinner spinner;

    private final JComboBox<String> fontNames;

    private final Domain domain;

    public FontSettingPanel(final Domain domain, final Setting<Font> setting) {
        super(setting);

        final Font font = domain.getSettingsController().getSettings().getFont(setting.getName());

        this.domain = domain;
        final JLabel descriptor = new JLabel(setting.getDescriptor());
        this.spinner = new JSpinner(new SpinnerNumberModel(font.getSize(), 1, 70, 1));
        this.fontNames = new JComboBox<>(FontUtils.getFixedWidthFontNames());

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        add(descriptor);
        add(Box.createHorizontalStrut(5));
        add(Box.createHorizontalGlue());
        add(fontNames);
        add(Box.createHorizontalStrut(5));
        add(spinner);

        spinner.addChangeListener(e -> updateFont());
        fontNames.addItemListener(e -> updateFont());
    }

    private void updateFont() {
        final Font newFont = new QuickWidthFont((String) fontNames.getSelectedItem(), Font.PLAIN, (int) spinner.getValue());
        domain.getSettingsController().getSettings().set(setting.getName(), newFont);
    }

    @Override
    public void setValue(final Object value) {
        final Font font = (Font) value;

        spinner.setValue(font.getSize());
        fontNames.setSelectedItem(font.getName());
    }
}
