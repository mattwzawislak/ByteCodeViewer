package org.obicere.bcviewer.context;

import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.bcviewer.gui.swing.SwingManager;

import java.util.logging.Level;
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

    public void logInfo(final String message) {
        logger.log(Level.INFO, message);
    }

    public void logConfig(final String message) {
        logger.log(Level.CONFIG, message);
    }

    public void logSevere(final String message) {
        logger.log(Level.SEVERE, message);
    }

    public void log(final Level level, final String message) {
        logger.log(level, message);
    }

    public String getApplicationName() {
        return APPLICATION_NAME;
    }

    public double getCurrentVersion() {
        return CURRENT_VERSION;
    }
}
