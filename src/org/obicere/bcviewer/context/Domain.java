package org.obicere.bcviewer.context;

import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.configuration.Paths;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.bcviewer.reader.ClassFileReader;

import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class Domain {

    private static final double CURRENT_VERSION = 0.00;

    private static final String APPLICATION_NAME = "Bytecode Viewer v" + CURRENT_VERSION;

    private final Logger logger = Logger.getGlobal();

    private ClassInformation classInformation = new ClassInformation();
    private ClassFileReader  classReader      = new ClassFileReader();
    private GUIManager guiManager;
    private Icons      icons;
    private Paths      paths;

    public void initialize() {
        this.guiManager = new GUIManager(this);
        this.icons = new Icons(this);
        this.paths = new Paths(this);
    }

    public ClassInformation getClassInformation() {
        return classInformation;
    }

    public ClassFileReader getClassReader() {
        return classReader;
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
