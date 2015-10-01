package org.obicere.bcviewer.configuration;

import org.obicere.bcviewer.concurrent.ClassCallback;
import org.obicere.bcviewer.concurrent.ClassLoaderService;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;

import java.io.File;

/**
 */
public class ClassFileLoader implements DomainAccess {

    private final Domain domain;

    public ClassFileLoader(final Domain domain) {
        this.domain = domain;
    }

    public void attemptLoad() {
        final GUIManager guiManager = domain.getGUIManager();
        final FrameManager frameManager = guiManager.getFrameManager();
        final File[] files = frameManager.promptForFiles(".class");

        if (files == null) {
            return;
        }

        final ClassLoaderService service = domain.getClassLoaderService();

        for (final File file : files) {
            final String name = file.getName();

            // -6 for ".class" extension
            final String className = name.substring(0, name.length() - 6);

            if (frameManager.hasEditorPanel(className)) {
                frameManager.displayEditorPanel(className);
                break;
            }
            final EditorPanel panel = frameManager.createEditorPanel(className);
            service.requestProcess(new ClassCallback(panel), file);
        }
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
