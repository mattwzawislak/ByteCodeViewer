package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;

import java.nio.file.Path;
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

    public Future<ClassInformation> postRequest(final ClassCallback callback, final Path file) {

        final FileLoadRequest request = new FileLoadRequest(callback, file);
        return classLoaderExecutorService.submit(request);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    private class FileLoadRequest implements Callable<ClassInformation> {

        private final ClassCallback callback;
        private final Path    file;

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
