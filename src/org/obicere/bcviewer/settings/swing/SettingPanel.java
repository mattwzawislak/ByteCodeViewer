package org.obicere.bcviewer.settings.swing;

import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.JPanel;

/**
 */
public abstract class SettingPanel<T> extends JPanel {

    protected final Setting<T> setting;

    public SettingPanel(final Setting<T> setting) {
        this.setting = setting;

        setting.addPropertyChangeListener(evt -> setValue(evt.getNewValue()));
    }

    public Setting<T> getSetting() {
        return setting;
    }

    public abstract void setValue(final Object value);
}
