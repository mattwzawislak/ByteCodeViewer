package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.settings.SettingModeler;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.JComponent;

/**
 */
public class BooleanSettingModeler implements SettingModeler<Boolean, JComponent> {

    private final Domain domain;

    public BooleanSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Boolean> setting) {
        return new BooleanSettingPanel(domain, setting);
    }
}
