package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.settings.SettingModeler;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.JComponent;

/**
 */
public class FloatSettingModeler implements SettingModeler<Float, JComponent> {

    private final Domain domain;

    public FloatSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Float> setting) {
        return new FloatSettingPanel(domain, setting);
    }
}
