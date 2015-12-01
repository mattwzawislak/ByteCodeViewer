package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.settings.SettingModeler;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.JComponent;

/**
 */
public class StringSettingModeler implements SettingModeler<String, JComponent> {

    private final Domain domain;

    public StringSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<String> setting) {
        return new StringSettingPanel(domain, setting);
    }
}
