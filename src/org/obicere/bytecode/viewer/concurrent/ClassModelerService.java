package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.dom.Block;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 */
public class ClassModelerService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 8;

    private final ExecutorService classModelerExecutorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);

    private final Domain domain;

    public ClassModelerService(final Domain domain) {
        this.domain = domain;
    }

    public Future<List<Block>> postRequest(final ClassCallback callback, final DocumentBuilder builder, final ClassInformation information) {
        final ClassModelRequest request = new ClassModelRequest(callback, builder, information);

        return classModelerExecutorService.submit(request);
    }

    @Override
    public Domain getDomain() {
        return domain;
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
                return blocks;
            } catch (final Throwable throwable) {
                callback.notifyThrowable(throwable);
                return null;
            }
        }
    }
}
