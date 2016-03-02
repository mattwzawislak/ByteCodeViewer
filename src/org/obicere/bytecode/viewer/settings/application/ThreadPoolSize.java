package org.obicere.bytecode.viewer.settings.application;

import org.obicere.bytecode.viewer.concurrent.ClassLoaderService;
import org.obicere.bytecode.viewer.concurrent.ClassModelerService;
import org.obicere.bytecode.viewer.concurrent.FileLoaderService;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.settings.Group;
import org.obicere.bytecode.viewer.settings.target.IntegerSetting;
import org.obicere.bytecode.viewer.settings.target.Setting;

/**
 */
public class ThreadPoolSize implements Group {

    private static final String NAME = "Thread Pool Size";

    private static final String SUFFIX = "thread.";

    private static final IntegerSetting CLASS_LOADER_SIZE  = new IntegerSetting(SUFFIX + "classLoader", "Class Loading", 4, 1, 32);
    private static final IntegerSetting CLASS_MODELER_SIZE = new IntegerSetting(SUFFIX + "classModeler", "Class Modeling", 4, 1, 32);
    private static final IntegerSetting FILE_LOADER_SIZE   = new IntegerSetting(SUFFIX + "fileLoader", "File Loading", 4, 1, 32);

    private static final Setting<?>[] SETTINGS = new Setting[]{
            CLASS_LOADER_SIZE,
            CLASS_MODELER_SIZE,
            FILE_LOADER_SIZE
    };

    public ThreadPoolSize(final Domain domain) {
        CLASS_LOADER_SIZE.addPropertyChangeListener(e -> {
            final ClassLoaderService service = domain.getClassLoaderService();
            service.setSize((int) e.getNewValue());
        });

        CLASS_MODELER_SIZE.addPropertyChangeListener(e -> {
            final ClassModelerService service = domain.getClassModelerService();
            service.setSize((int) e.getNewValue());
        });

        FILE_LOADER_SIZE.addPropertyChangeListener(e -> {
            final FileLoaderService service = domain.getFileLoaderService();
            service.setSize((int) e.getNewValue());
        });
    }

    @Override
    public String getGroupName() {
        return NAME;
    }

    @Override
    public Setting<?>[] getSettings() {
        return SETTINGS;
    }
}
