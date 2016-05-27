package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.core.io.LeafSource;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.reader.ClassFileReader;
import org.obicere.bytecode.core.util.IndexedDataInputStream;
import org.obicere.bytecode.viewer.configuration.FileHashStorage;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class ClassLoaderService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 4;

    private static final String NAME = "classLoader";

    private final Domain domain;

    private final ThreadPoolExecutor service;

    public ClassLoaderService(final Domain domain) {
        this.domain = domain;
        this.service = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_COUNT, new NamedThreadFactory(NAME));
    }

    public Future<ClassInformation> postRequest(final Callback callback, final LeafSource source) {
        final FileLoadRequest request = new FileLoadRequest(callback, source);

        return service.submit(request);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    public void setSize(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("class loader pool size must positive");
        }
        service.setCorePoolSize(size);
        service.setMaximumPoolSize(size);
    }

    private class FileLoadRequest implements Callable<ClassInformation> {

        private final Callback   callback;
        private final LeafSource source;

        public FileLoadRequest(final Callback callback, final LeafSource source) {
            this.callback = callback;
            this.source = source;
        }

        @Override
        public ClassInformation call() {
            try {
                final String sourceName = source.getPath();
                final byte[] bytes = source.read();

                final FileHashStorage storage = domain.getFileHashStorage();
                final boolean present = storage.registerIfAbsent(sourceName, bytes);

                if (present) {
                    return null;
                }

                final IndexedDataInputStream input = new IndexedDataInputStream(bytes);
                final ClassFileReader reader = domain.getClassReader();

                final ClassFile file = reader.read(input);
                final ClassInformation classInformation = new ClassInformation(domain, file, source);

                domain.getClassStorage().publish(file.getName(), classInformation);
                domain.getGUIManager().getFrameManager().getEditorManager().addClass(classInformation);

                return classInformation;
            } catch (final Throwable throwable) {
                Logger.getGlobal().log(Level.SEVERE, "Error while loading class from file: " + source);
                callback.notifyThrowable(throwable);
                return null;
            } finally {
                callback.notifyCompletion();
            }
        }
    }

}
