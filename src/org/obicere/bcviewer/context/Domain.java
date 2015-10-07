package org.obicere.bcviewer.context;

import org.obicere.bcviewer.concurrent.ClassLoaderService;
import org.obicere.bcviewer.concurrent.ClassModelerService;
import org.obicere.bcviewer.configuration.ClassFileLoader;
import org.obicere.bcviewer.configuration.Icons;
import org.obicere.bcviewer.configuration.Paths;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.bcviewer.reader.ClassFileReader;
import org.obicere.bcviewer.settings.SettingsController;

import java.util.logging.Logger;

/**
 * @author Obicere
 */
public final class Domain {

    private static final double CURRENT_VERSION = 0.00;

    private static final String APPLICATION_NAME = "Bytecode Viewer v" + CURRENT_VERSION;

    private final Logger logger = Logger.getGlobal();

    private ClassLoaderService  classLoaderService;
    private ClassModelerService classModelerService;

    private ClassFileLoader classLoader;
    private ClassFileReader classReader = new ClassFileReader();
    private GUIManager         guiManager;
    private Icons              icons;
    private Paths              paths;
    private SettingsController settings;

    public Domain() {
        this.classLoaderService = new ClassLoaderService(this);
        this.classModelerService = new ClassModelerService(this);

        this.classLoader = new ClassFileLoader(this);
        this.guiManager = new GUIManager(this);
        this.icons = new Icons(this);
        this.paths = new Paths(this);
        this.settings = new SettingsController(this);
    }

    public ClassLoaderService getClassLoaderService() {
        return classLoaderService;
    }

    public ClassModelerService getClassModelerService() {
        return classModelerService;
    }

    public ClassFileLoader getClassLoader() {
        return classLoader;
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

    public SettingsController getSettingsController() {
        return settings;
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
