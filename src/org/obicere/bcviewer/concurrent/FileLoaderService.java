package org.obicere.bcviewer.concurrent;

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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 */
public class FileLoaderService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 2;

    private final ExecutorService fileLoaderExecutorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);

    private final Domain domain;

    public FileLoaderService(final Domain domain) {
        this.domain = domain;
    }

    public void postRequest(final File... files) {
        if (files == null) {
            return;
        }
        final FileLoadRequest newRequest = new FileLoadRequest(files);
        fileLoaderExecutorService.submit(newRequest);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    private class FileLoadRequest implements Callable<Void> {

        private final File[] files;

        public FileLoadRequest(final File[] files) {
            this.files = files;
        }

        @Override
        public Void call() throws Exception {
            final GUIManager guiManager = domain.getGUIManager();
            final FrameManager frameManager = guiManager.getFrameManager();
            final EditorPanelManager editorPanels = frameManager.getEditorManager();
            for (final File file : files) {
                if (file.isDirectory()) {
                    postRequest(file.listFiles());
                    continue;
                }
                final String extension = FileUtils.getFileExtension(file);
                if (extension == null) {
                    continue;
                }

                switch (extension) {
                    case ".class":
                        loadClass(editorPanels, new BasicFileSource(file));
                        break;
                    case ".zip":
                    case ".rar":
                    case ".jar":
                        loadArchive(editorPanels, file);
                }
            }

            return null;
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
        final EditorPanel panel = editorPanels.createEditorPanel();
        service.postRequest(new ClassCallback(panel), file);
    }

    private void loadArchive(final EditorPanelManager editorPanels, final File file) {
        try {
            final ZipFile zip = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                final String name = entry.getName();
                final String extension = FileUtils.getFileExtension(name);
                if (extension != null && extension.equals(".class")) {
                    loadClass(editorPanels, new ArchiveFileSource(zip, entry));
                }
            }
        } catch (final IOException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
