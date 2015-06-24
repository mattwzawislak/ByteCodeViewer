package org.obicere.bcviewer.context;

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

    public Domain() {
        this.guiManager = new GUIManager(this);
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }

    public Logger getLogger(){
        return logger;
    }

    public String getApplicationName() {
        return APPLICATION_NAME;
    }

    public double getCurrentVersion() {
        return CURRENT_VERSION;
    }
}
