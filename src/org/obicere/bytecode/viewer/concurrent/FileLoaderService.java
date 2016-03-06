package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.gui.EditorPanelManager;
import org.obicere.bytecode.viewer.gui.FrameManager;
import org.obicere.bytecode.viewer.gui.GUIManager;
import org.obicere.bytecode.viewer.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 */
public class FileLoaderService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 2;

    private static final String NAME = "fileLoader";

    private final Domain domain;

    private final ThreadPoolExecutor service;

    public FileLoaderService(final Domain domain) {
        this.domain = domain;
        this.service = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_COUNT, new NamedThreadFactory(NAME));
    }

    public void postRequest(final Callback callback, final Path... files) {
        if (files == null || files.length == 0) {
            return;
        }
        final FileLoadRequest newRequest = new FileLoadRequest(callback, files);

        service.submit(newRequest);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    public void setSize(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("file loader pool size must positive");
        }
        service.setCorePoolSize(size);
        service.setMaximumPoolSize(size);
    }

    private class FileLoadRequest implements Callable<Void> {

        private final Callback callback;
        private final Path[]   paths;

        public FileLoadRequest(final Callback callback, final Path[] paths) {
            this.callback = callback;
            this.paths = paths;
        }

        @Override
        public Void call() {
            final GUIManager guiManager = domain.getGUIManager();
            final FrameManager frameManager = guiManager.getFrameManager();
            final EditorPanelManager editorPanels = frameManager.getEditorManager();
            for (final Path path : paths) {
                try {
                    final File file = path.toFile();
                    if (!file.exists() || !file.canRead()) {
                        continue;
                    }
                    if (file.isDirectory()) {
                        final File[] files = file.listFiles();
                        if (files == null) {
                            continue;
                        }
                        final Path[] paths = new Path[files.length];

                        for (int i = 0; i < files.length; i++) {
                            paths[i] = files[i].toPath();
                        }
                        postRequest(callback, paths);
                        continue;
                    }
                    final String extension = FileUtils.getFileExtension(path.toString());
                    if (extension == null) {
                        continue;
                    }

                    switch (extension) {
                        case ".class":
                            loadClass(editorPanels, path);
                            break;
                        case ".zip":
                        case ".jar":
                            loadArchive(editorPanels, path);
                    }
                } catch (final Throwable throwable) {
                    callback.notifyThrowable(throwable);
                    Logger.getGlobal().log(Level.SEVERE, "Error performing file I/O on: " + path);
                }
            }
            callback.notifyCompletion();
            return null;
        }
    }

    private void loadClass(final EditorPanelManager editorPanels, final Path path) {
        final ClassLoaderService service = domain.getClassLoaderService();

        final String name = path.toString();
        final String className = FileUtils.getFileName(name);

        if (editorPanels.hasEditorPanel(className)) {
            editorPanels.displayEditorPanel(className);
            return;
        }
        service.postRequest(new RequestCallback(), path);
    }

    private void loadArchive(final EditorPanelManager editorPanels, final Path path) throws IOException {

        final ZipFile zip = new ZipFile(path.toFile());
        final Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            final String name = entry.getName();
            final String extension = FileUtils.getFileExtension(name);
            if (extension != null && extension.equals(".class")) {

                final FileSystem system = FileSystems.newFileSystem(path, null);
                final Path newPath = system.getPath(name);
                loadClass(editorPanels, newPath);
            }
        }
    }
}
