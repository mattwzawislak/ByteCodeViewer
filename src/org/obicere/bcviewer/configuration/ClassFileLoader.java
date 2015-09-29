package org.obicere.bcviewer.configuration;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.utility.io.IOUtils;

import java.io.File;
import java.io.IOException;

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

        for (final File file : files) {
            final String name = file.getName();

            // -6 for ".class" extension
            final String className = name.substring(0, name.length() - 6);

            if (frameManager.hasEditorPanel(className)) {
                frameManager.displayEditorPanel(className);
                return;
            }

            final ClassFile classFile;
            final byte[] bytes;

            try {
                bytes = IOUtils.readData(file);
                classFile = domain.getClassInformation().load(file);
            } catch (final IOException e) {
                e.printStackTrace();
                return;
            }

            final EditorPanel panel = frameManager.createEditorPanel(className);

            panel.setClassBytes(bytes);
            panel.setClassFile(classFile);

            frameManager.displayEditorPanel(className);
        }
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
