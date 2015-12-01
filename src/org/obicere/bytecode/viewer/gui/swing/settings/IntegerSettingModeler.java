package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.settings.SettingModeler;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.JComponent;

/**
 */
public class IntegerSettingModeler implements SettingModeler<Integer, JComponent> {

    private final Domain domain;

    public IntegerSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Integer> setting) {
        return new IntegerSettingPanel(domain, setting);
    }
}
