package org.obicere.bcviewer.configuration;

import org.obicere.bcviewer.concurrent.ClassCallback;
import org.obicere.bcviewer.concurrent.ClassLoaderService;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.gui.EditorPanel;
import org.obicere.bcviewer.gui.EditorPanelManager;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.bcviewer.util.FileUtils;
import org.obicere.utility.io.ArchiveFileSource;
import org.obicere.utility.io.BasicFileSource;
import org.obicere.utility.io.FileSource;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
        final GUIManager guiManager = domain.getGUIManager();
        final FrameManager frameManager = guiManager.getFrameManager();
        final EditorPanelManager editorPanels = frameManager.getEditorManager();

        for (final File file : files) {
            if (file.isDirectory()) {
                load(file.listFiles());
                continue;
            }
            final String extension = getExtension(file);
            if (extension == null) {
                continue;
            }

            switch (extension) {
                case CLASS_EXTENSION:
                    loadClass(editorPanels, new BasicFileSource(file));
                    break;
                case ZIP_EXTENSION:
                case RAR_EXTENSION:
                case JAR_EXTENSION:
                    loadArchive(editorPanels, file);
            }
        }
    }

    private void loadClass(final EditorPanelManager editorPanels, final FileSource file) {
        final ClassLoaderService service = domain.getClassLoaderService();

        final String path = file.getPath();
        final String className = FileUtils.getFileName(path);

        if (editorPanels.hasEditorPanel(className)) {
            editorPanels.displayEditorPanel(className);
            return;
        }
        final EditorPanel panel = editorPanels.createEditorPanel(className);
        service.postRequest(new ClassCallback(panel), file);
    }

    private void loadArchive(final EditorPanelManager editorPanels, final File file) {
        try {
            final ZipFile zip = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                final String name = entry.getName();
                final String extension = getExtension(name);
                if (extension != null && extension.equals(CLASS_EXTENSION)) {
                    loadClass(editorPanels, new ArchiveFileSource(zip, entry));
                }
            }
        } catch (final IOException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private String getExtension(final File file) {
        return getExtension(file.getName());
    }

    private String getExtension(final String name) {
        final int dotIndex = name.lastIndexOf('.');
        if (dotIndex <= 0) {
            return null;
        }
        return name.substring(dotIndex);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
