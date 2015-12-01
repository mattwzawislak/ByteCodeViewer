package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.settings.SettingModeler;
import org.obicere.bytecode.viewer.settings.target.Setting;

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
