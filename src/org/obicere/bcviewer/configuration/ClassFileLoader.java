package org.obicere.bcviewer.configuration;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;

import java.io.File;

/**
 */
public class ClassFileLoader implements DomainAccess {

    private static final String CLASS_EXTENSION = ".class";

    private static final String JAR_EXTENSION = ".jar";

    private static final String ZIP_EXTENSION = ".zip";

    private static final String RAR_EXTENSION = ".rar";

    private static final String[] EXTENSIONS = new String[]{
            CLASS_EXTENSION,
            JAR_EXTENSION,
            ZIP_EXTENSION,
            RAR_EXTENSION
    };

    private final Domain domain;

    public ClassFileLoader(final Domain domain) {
        this.domain = domain;
    }

    public void promptAndLoad() {
        final GUIManager guiManager = domain.getGUIManager();
        final FrameManager frameManager = guiManager.getFrameManager();
        final File[] files = frameManager.promptForFiles(EXTENSIONS);

        load(files);
    }

    public void load(final File... files) {
        if (files == null) {
            return;
        }
        domain.getFileLoaderService().postRequest(files);
    }


    @Override
    public Domain getDomain() {
        return domain;
    }
}
