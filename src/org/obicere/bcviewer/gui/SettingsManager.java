package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.gui.settings.SettingModeler;
import org.obicere.bcviewer.settings.target.Setting;

/**
 */
public interface SettingsManager<C> {

    public boolean isVisible();

    public void setVisible(final boolean visible);

    public void dispose();

    public <T> void addModeler(final Class<? extends Setting<T>> cls, final SettingModeler<T, C> modeler);

}
