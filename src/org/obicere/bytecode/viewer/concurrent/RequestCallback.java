package org.obicere.bytecode.viewer.concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class RequestCallback implements Callback {

    public void notifyThrowable(final Throwable error) {
        Logger.getGlobal().log(Level.WARNING, error.getMessage(), error);
    }

    @Override
    public void notifyCompletion() {

    }
}
