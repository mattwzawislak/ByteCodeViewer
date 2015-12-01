package org.obicere.bytecode.viewer.gui;

import java.io.File;

/**
 * @author Obicere
 */
public interface FrameManager {

    public void open();

    public void close();

    public void requestFocus();

    public String getTitle();

    public void setTitle(final String name);

    public void setSize(final int width, final int height);

    public void paint();

    public void validate();

    public void pack();

    public EditorPanelManager getEditorManager();

    public File[] promptForFiles(final String... extensions);

    public SettingsManager<?> getSettingsManager();
}
