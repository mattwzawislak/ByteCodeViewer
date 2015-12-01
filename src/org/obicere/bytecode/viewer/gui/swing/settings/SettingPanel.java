package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.JPanel;

/**
 */
public abstract class SettingPanel<T> extends JPanel {

    protected final Setting<T> setting;

    public SettingPanel(final Setting<T> setting) {
        this.setting = setting;

        setting.addPropertyChangeListener(evt -> setValue(evt.getNewValue()));
    }

    @Override
    public void addNotify() {
        super.addNotify();

        // we need to set the initial value for the setting
        final T value = setting.getValue();
        if (value == null) {
            setValue(setting.getDefaultValue());
        } else {
            setValue(value);
        }
    }

    public Setting<T> getSetting() {
        return setting;
    }

    public abstract void setValue(final Object value);
}
