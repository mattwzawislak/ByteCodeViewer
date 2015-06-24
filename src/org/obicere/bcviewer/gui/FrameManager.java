package org.obicere.bcviewer.gui;

/**
 * @author Obicere
 */
public interface FrameManager {

    public Object getWindow();

    public Object getComponent(final String name);

    public void open();

    public void close();

    public void requestFocus();

    public String getTitle();

    public void setTitle(final String name);

    public void setSize(final int width, final int height);

    public void paint();

    public void validate();

    public void pack();

    public String getDefaultThemeName();

    public String[] getAvailableThemeNames();

    public void loadDefaultTheme();

    public void loadTheme(final String name);

}
