package org.obicere.bcviewer.startup;

import org.obicere.bcviewer.Boot;
import org.obicere.bcviewer.startup.application.ProvideGroups;

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
