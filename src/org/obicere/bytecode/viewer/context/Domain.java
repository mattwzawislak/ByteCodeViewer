package org.obicere.bytecode.viewer.context;

import org.obicere.bytecode.core.reader.JCClassReader;
import org.obicere.bytecode.viewer.concurrent.ClassLoaderService;
import org.obicere.bytecode.viewer.concurrent.ClassModelerService;
import org.obicere.bytecode.viewer.concurrent.FileLoaderService;
import org.obicere.bytecode.viewer.concurrent.MetaClassLoaderService;
import org.obicere.bytecode.viewer.configuration.ClassFileLoader;
import org.obicere.bytecode.viewer.configuration.ClassStorage;
import org.obicere.bytecode.viewer.configuration.FileHashStorage;
import org.obicere.bytecode.viewer.configuration.Icons;
import org.obicere.bytecode.viewer.configuration.Paths;
import org.obicere.bytecode.viewer.gui.GUIManager;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.settings.SettingsController;

import java.util.logging.Logger;

/**
 * @author Obicere
 */
public final class Domain {

    private static final double CURRENT_VERSION = 0.00;

    private static final String APPLICATION_NAME = "ByteCode Viewer v" + CURRENT_VERSION;

    private final Logger logger = Logger.getGlobal();

    private final ClassLoaderService     classLoaderService;
    private final ClassModelerService    classModelerService;
    private final FileLoaderService      fileLoaderService;
    private final MetaClassLoaderService metaClassLoaderService;

    private final ClassStorage classStorage;

    private final FileHashStorage fileHashStorage;

    private final ClassFileLoader classLoader;
    private final JCClassReader   classReader;
    private final ModelerSet      modelers;

    private final GUIManager         guiManager;
    private final Icons              icons;
    private final Paths              paths;
    private final SettingsController settings;

    public Domain() {
        this.classLoaderService = new ClassLoaderService(this);
        this.classModelerService = new ClassModelerService(this);
        this.fileLoaderService = new FileLoaderService(this);
        this.metaClassLoaderService = new MetaClassLoaderService(this);

        this.classStorage = new ClassStorage();
        this.fileHashStorage = new FileHashStorage();

        this.classLoader = new ClassFileLoader(this);
        this.classReader = new JCClassReader();
        this.modelers = new ModelerSet();

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

    public FileLoaderService getFileLoaderService() {
        return fileLoaderService;
    }

    public MetaClassLoaderService getMetaClassLoaderService() {
        return metaClassLoaderService;
    }

    public ClassStorage getClassStorage() {
        return classStorage;
    }

    public FileHashStorage getFileHashStorage() {
        return fileHashStorage;
    }

    public ClassFileLoader getClassLoader() {
        return classLoader;
    }

    public JCClassReader getClassReader() {
        return classReader;
    }

    public ModelerSet getModelers() {
        return modelers;
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
