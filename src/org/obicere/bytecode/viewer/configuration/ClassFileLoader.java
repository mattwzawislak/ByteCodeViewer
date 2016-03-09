package org.obicere.bytecode.viewer.configuration;

import org.obicere.bytecode.viewer.concurrent.RequestCallback;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.gui.FrameManager;
import org.obicere.bytecode.viewer.gui.GUIManager;

import java.io.File;

/**
 */
public class ClassFileLoader implements DomainAccess {

    private static final String CLASS_EXTENSION = ".class";

    private static final String JAR_EXTENSION = ".jar";

    private static final String ZIP_EXTENSION = ".zip";

    private static final String[] EXTENSIONS = new String[]{
            CLASS_EXTENSION,
            JAR_EXTENSION,
            ZIP_EXTENSION
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
        domain.getFileLoaderService().postRequest(new RequestCallback(), files);
    }


    @Override
    public Domain getDomain() {
        return domain;
    }
}
