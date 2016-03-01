package org.obicere.bytecode.viewer.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(1);

    private final String suffix;

    public NamedThreadFactory(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("name must be non-null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        suffix = name + "-thread-";
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        return new Thread(runnable, suffix + counter.getAndIncrement());
    }
}
