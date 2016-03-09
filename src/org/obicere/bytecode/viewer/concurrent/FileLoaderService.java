package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.gui.EditorPanelManager;
import org.obicere.bytecode.viewer.gui.FrameManager;
import org.obicere.bytecode.viewer.gui.GUIManager;
import org.obicere.bytecode.viewer.io.FileSource;
import org.obicere.bytecode.viewer.io.Source;
import org.obicere.bytecode.viewer.io.ZipFileSource;
import org.obicere.bytecode.viewer.util.FileUtils;

import java.io.File;
import java.io.IOException;
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

    public void postRequest(final Callback callback, final File... files) {
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
        private final File[]   paths;

        public FileLoadRequest(final Callback callback, final File[] paths) {
            this.callback = callback;
            this.paths = paths;
        }

        @Override
        public Void call() {
            for (final File file : paths) {
                try {
                    if (!file.exists() || !file.canRead()) {
                        continue;
                    }
                    if (file.isDirectory()) {
                        final File[] files = file.listFiles();
                        if (files == null) {
                            continue;
                        }
                        postRequest(callback, files);
                        continue;
                    }
                    final String name = file.getAbsolutePath();
                    final String extension = FileUtils.getFileExtension(name);
                    if (extension == null) {
                        continue;
                    }

                    switch (extension) {
                        case ".class":
                            loadClass(file);
                            break;
                        case ".zip":
                        case ".jar":
                            loadArchive(file);
                    }
                } catch (final Throwable throwable) {
                    callback.notifyThrowable(throwable);
                    Logger.getGlobal().log(Level.SEVERE, "Error performing file I/O on: " + file);
                }
            }
            callback.notifyCompletion();
            return null;
        }
    }

    private void loadClass(final File path) {

        final String name = path.toString();
        final String className = FileUtils.getFileName(name);
        requestLoad(new FileSource(path), className);
    }

    private void loadArchive(final File path) throws IOException {
        final String zipFile = path.getAbsolutePath();

        final ZipFile zip = new ZipFile(path);
        final Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            final String name = entry.getName();
            final String extension = FileUtils.getFileExtension(name);
            if (extension != null && extension.equals(".class")) {

                requestLoad(new ZipFileSource(zipFile, name), name);
            }
        }
    }

    private void requestLoad(final Source source, final String className){
        final GUIManager guiManager = domain.getGUIManager();
        final FrameManager frameManager = guiManager.getFrameManager();
        final EditorPanelManager editorPanels = frameManager.getEditorManager();

        final ClassLoaderService service = domain.getClassLoaderService();

        if (editorPanels.hasEditorPanel(className)) {
            editorPanels.displayEditorPanel(className);
        }     else {
            service.postRequest(new RequestCallback(), source);
        }
    }
}
