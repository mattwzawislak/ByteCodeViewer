package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.settings.SettingModeler;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.JComponent;

/**
 */
public class LongSettingModeler implements SettingModeler<Long, JComponent> {

    private final Domain domain;

    public LongSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Long> setting) {
        return new LongSettingPanel(domain, setting);
    }
}
