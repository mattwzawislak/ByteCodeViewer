package org.obicere.bytecode.viewer.startup;

import org.obicere.bytecode.viewer.Boot;
import org.obicere.bytecode.viewer.startup.application.ProvideAttributeModelers;
import org.obicere.bytecode.viewer.startup.application.ProvideElementValueModelers;
import org.obicere.bytecode.viewer.startup.application.ProvideFrameModelers;
import org.obicere.bytecode.viewer.startup.application.ProvideGroups;
import org.obicere.bytecode.viewer.startup.application.ProvideTypeModelers;

/**
 */
public class StartUpTaskLoader {

    public void loadStartUpTasks() {
        addApplicationDefaults();
    }

    private void addApplicationDefaults() {
        final StartUpQueue queue = Boot.getStartUpQueue();

        // add settings startup
        queue.provide(new ProvideGroups());

        // add modelers
        queue.provide(new ProvideAttributeModelers());
        queue.provide(new ProvideElementValueModelers());
        queue.provide(new ProvideFrameModelers());
        queue.provide(new ProvideTypeModelers());

    }
}
