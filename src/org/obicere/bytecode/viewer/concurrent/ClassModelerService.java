package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.dom.Block;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 */
public class ClassModelerService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 8;

    private static final String NAME = "classModeler";

    private final Domain domain;

    private final ThreadPoolExecutor service;

    public ClassModelerService(final Domain domain) {
        this.domain = domain;
        this.service = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_COUNT, new NamedThreadFactory(NAME));
    }

    public Future<List<Block>> postRequest(final ClassCallback callback, final DocumentBuilder builder, final ClassInformation information) {
        final ClassModelRequest request = new ClassModelRequest(callback, builder, information);

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

    private class ClassModelRequest implements Callable<List<Block>> {

        private final ClassCallback    callback;
        private final DocumentBuilder  builder;
        private final ClassInformation information;

        public ClassModelRequest(final ClassCallback callback, final DocumentBuilder builder, final ClassInformation information) {
            this.callback = callback;
            this.builder = builder;
            this.information = information;
        }

        @Override
        public List<Block> call() throws Exception {
            try {
                final List<Block> blocks = builder.build(information);

                callback.notifyCompletion(blocks);

                domain.getGUIManager().getFrameManager().getEditorManager().displayEditorPanel(information.getRootClass().getName());
                return blocks;
            } catch (final Throwable throwable) {
                callback.notifyThrowable(throwable);
                return null;
            }
        }
    }
}
