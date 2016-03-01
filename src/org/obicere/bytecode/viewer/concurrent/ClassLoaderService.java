package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.settings.Settings;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ClassLoaderService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 4;

    private final Domain domain;

    private volatile ExecutorService service;

    private final ReentrantLock serviceLock = new ReentrantLock();

    public ClassLoaderService(final Domain domain) {
        this.domain = domain;

        final Settings settings = domain.getSettingsController().getSettings();
        final int size = settings.getInteger("thread.classLoader", THREAD_POOL_COUNT);
        setSize(size);
    }

    public Future<ClassInformation> postRequest(final ClassCallback callback, final Path file) {
        try {
            final FileLoadRequest request = new FileLoadRequest(callback, file);

            serviceLock.lock();
            return service.submit(request);
        } finally {
            serviceLock.unlock();
        }
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    public void setSize(final int size) {
        try {
            if (size <= 0) {
                throw new IllegalArgumentException("class loader pool size must positive");
            }
            serviceLock.lock();

            service = Executors.newFixedThreadPool(size);
        } finally {
            serviceLock.unlock();
        }
    }

    private class FileLoadRequest implements Callable<ClassInformation> {

        private final ClassCallback callback;
        private final Path          file;

        public FileLoadRequest(final ClassCallback callback, final Path file) {
            this.callback = callback;
            this.file = file;
        }

        @Override
        public ClassInformation call() throws Exception {
            final ClassInformation classInformation = new ClassInformation(domain);

            classInformation.load(callback, file);

            final ClassFile rootClass = classInformation.getRootClass();

            domain.getGUIManager().getFrameManager().getEditorManager().addEditorPanel(callback.getEditorPanel(), rootClass.getName());

            final ClassModelerService service = domain.getClassModelerService();
            service.postRequest(callback, callback.getEditorPanel().getBuilder(), classInformation);

            return classInformation;
        }
    }

}
