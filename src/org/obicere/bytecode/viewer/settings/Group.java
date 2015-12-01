package org.obicere.bytecode.viewer.settings;

import org.obicere.bytecode.viewer.settings.target.Setting;

/**
 */
public interface Group {

    public String getGroupName();

    public Setting<?>[] getSettings();

}
