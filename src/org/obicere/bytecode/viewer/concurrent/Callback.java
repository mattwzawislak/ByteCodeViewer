package org.obicere.bytecode.viewer.concurrent;

/**
 */
public interface Callback {
    public void notifyThrowable(final Throwable error);

    public void notifyCompletion();

}
