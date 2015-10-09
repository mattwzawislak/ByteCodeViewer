package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.settings.SettingModeler;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.JComponent;
import java.awt.Font;

/**
 */
public class FontSettingModeler implements SettingModeler<Font, JComponent> {

    private final Domain domain;

    public FontSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Font> setting) {
        return new FontSettingPanel(domain, setting);
    }
}
