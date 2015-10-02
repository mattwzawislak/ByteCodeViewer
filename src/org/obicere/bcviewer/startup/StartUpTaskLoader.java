package org.obicere.bcviewer.startup;

import org.obicere.bcviewer.Boot;

/**
 */
public class StartUpTaskLoader {

    public void loadStartUpTasks(){
        addApplicationDefaults();
    }

    private void addApplicationDefaults(){
        final StartUpQueue queue = Boot.getStartUpQueue();

        queue.provide(new ProvideGroups());
    }
}
