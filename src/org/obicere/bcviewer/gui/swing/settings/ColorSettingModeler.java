package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.settings.SettingModeler;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.JComponent;
import java.awt.Color;

/**
 */
public class ColorSettingModeler implements SettingModeler<Color, JComponent> {

    private final Domain domain;

    public ColorSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Color> setting) {
        return new ColorSettingPanel(domain, setting);
    }
}
