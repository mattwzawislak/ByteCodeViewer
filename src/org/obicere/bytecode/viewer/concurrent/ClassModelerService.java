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
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ClassModelerService implements DomainAccess {

    private static final int THREAD_POOL_COUNT = 8;

    private final Domain domain;

    private volatile ExecutorService service;

    private final ReentrantLock serviceLock = new ReentrantLock();

    public ClassModelerService(final Domain domain) {
        this.domain = domain;
        this.service = Executors.newFixedThreadPool(THREAD_POOL_COUNT);

    }

    public Future<List<Block>> postRequest(final ClassCallback callback, final DocumentBuilder builder, final ClassInformation information) {
        try {
            final ClassModelRequest request = new ClassModelRequest(callback, builder, information);

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
