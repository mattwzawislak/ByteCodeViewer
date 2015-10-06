package org.obicere.bcviewer.gui.settings;

import org.obicere.bcviewer.settings.target.Setting;

/**
 */
public interface SettingModeler<S, C> {

    public C model(final Setting<S> setting);

}
