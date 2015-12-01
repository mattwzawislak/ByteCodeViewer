package org.obicere.bytecode.viewer.gui.settings;

import org.obicere.bytecode.viewer.settings.target.Setting;

/**
 */
public interface SettingModeler<S, C> {

    public C model(final Setting<S> setting);

}
