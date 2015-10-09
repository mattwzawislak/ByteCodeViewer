package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.settings.SettingModeler;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.JComponent;

/**
 */
public class DoubleSettingModeler implements SettingModeler<Double, JComponent> {

    private final Domain domain;

    public DoubleSettingModeler(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public JComponent model(final Setting<Double> setting) {
        return new DoubleSettingPanel(domain, setting);
    }
}
