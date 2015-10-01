package org.obicere.bcviewer.settings;

import org.obicere.bcviewer.settings.target.Setting;

/**
 */
public interface Group {

    public String getGroupName();

    public Setting<?>[] getSettings();

}
