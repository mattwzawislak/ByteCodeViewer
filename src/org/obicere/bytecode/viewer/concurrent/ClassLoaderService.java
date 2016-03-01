package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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

    public Future<ClassInformation> postRequest(final ClassCallback callback, final Path file) {
        final FileLoadRequest request = new FileLoadRequest(callback, file);

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

            final ClassFile root = classInformation.getRootClass();
            domain.getClassStorage().publish(root.getName(), classInformation);

            domain.getGUIManager().getFrameManager().getEditorManager().addClass(classInformation);

            return classInformation;
        }
    }

}
