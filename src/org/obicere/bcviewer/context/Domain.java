package org.obicere.bcviewer.context;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.configuration.Paths;
import org.obicere.bcviewer.gui.GUIManager;

import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class Domain {

    private static final double CURRENT_VERSION = 0.00;

    private static final String APPLICATION_NAME = "Bytecode Viewer v" + CURRENT_VERSION;

    private final Logger logger = Logger.getGlobal();

    private GUIManager guiManager;
    private Paths      paths;
    private Icons      icons;

    public void initialize() {
        this.paths = new Paths(this);

        this.icons = new Icons(this);

        this.guiManager = new GUIManager(this);
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }

    public Paths getPaths() {
        return paths;
    }

    public Icons getIcons() {
        return icons;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getApplicationName() {
        return APPLICATION_NAME;
    }

    public double getCurrentVersion() {
        return CURRENT_VERSION;
    }
}
