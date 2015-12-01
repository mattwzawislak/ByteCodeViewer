package org.obicere.bytecode.viewer.startup;

import org.obicere.bytecode.viewer.Boot;
import org.obicere.bytecode.viewer.startup.application.ProvideGroups;

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
