package org.obicere.bcviewer.concurrent;

import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 */
public class ClassLoaderService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 4;

    private final Domain domain;

    public ClassLoaderService(final Domain domain) {
        this.domain = domain;
    }

    private final ExecutorService classLoaderExecutorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);

    public Future<ClassInformation> requestProcess(final ClassCallback callback, final File file) {

        final FileLoadRequest request = new FileLoadRequest(callback, file);
        return classLoaderExecutorService.submit(request);

    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    private class FileLoadRequest implements Callable<ClassInformation> {

        private final ClassCallback callback;
        private final File          file;

        public FileLoadRequest(final ClassCallback callback, final File file) {
            this.callback = callback;
            this.file = file;
        }

        @Override
        public ClassInformation call() throws Exception {
            final ClassInformation classInformation = new ClassInformation(domain);

            classInformation.load(callback, file);

            final ClassModelerService service = domain.getClassModelerService();
            service.postRequest(callback, callback.getEditorPanel().getBuilder(), classInformation);

            return classInformation;
        }
    }

}
