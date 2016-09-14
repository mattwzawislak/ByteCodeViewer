package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.core.io.LeafSource;
import org.obicere.bytecode.core.objects.meta.MetaClass;
import org.obicere.bytecode.core.reader.meta.MetaClassFileReader;
import org.obicere.bytecode.core.util.IndexedDataInputStream;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class MetaClassLoaderService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 4;

    private static final String NAME = "metaClassLoader";

    private final Domain domain;

    private final ThreadPoolExecutor service;

    private final MetaClassFileReader reader = new MetaClassFileReader();

    public MetaClassLoaderService(final Domain domain) {
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
                final InputStream stream = source.getInputStream();
                final IndexedDataInputStream input = new IndexedDataInputStream(stream);

                final MetaClass classFile = reader.read(input);

                input.close();

                final ClassInformation classInformation = new ClassInformation(domain, classFile, source);

                domain.getGUIManager().getFrameManager().getEditorManager().addClass(classInformation);
                domain.getClassStorage().publish(classFile.getName(), classInformation);

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
