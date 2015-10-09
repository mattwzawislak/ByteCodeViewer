package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.gui.settings.SettingModeler;

/**
 */
public interface SettingsManager<C> {

    public void initialize();

    public boolean isVisible();

    public void setVisible(final boolean visible);

    public void dispose();

    public void addModeler(final String id, final SettingModeler<?, C> modeler);

}
