package org.obicere.bcviewer.startup;

import org.obicere.bcviewer.context.Domain;

/**
 */
public interface StartUpTask {

    public int getPriority();

    public void call(final Domain domain);

}
