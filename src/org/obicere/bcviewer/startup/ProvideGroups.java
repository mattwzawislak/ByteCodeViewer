package org.obicere.bcviewer.startup;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.settings.SettingsController;
import org.obicere.bcviewer.settings.application.EditorGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class ProvideGroups implements StartUpTask {

    @Override
    public int getPriority() {
        return StartUpQueue.STANDARD_PRIORITY;
    }

    @Override
    public void call(final Domain domain) {
        final Logger logger = domain.getLogger();

        final List<String> groupClassNames = new LinkedList<>();

        groupClassNames.add(EditorGroup.class.getName());

        final String groupsFile = domain.getPaths().getSettingsGroupsFile();
        final File file = new File(groupsFile);

        try {
            if (!file.exists()) {
                logger.log(Level.INFO, "Groups file does not exist. Creating a new one.");
                if (!file.createNewFile()) {
                    logger.log(Level.WARNING, "Failed to create group file: " + file.getAbsolutePath());
                }
            }
            final BufferedReader reader = new BufferedReader(new FileReader(file));

            String next;
            while ((next = reader.readLine()) != null) {
                groupClassNames.add(next);
            }

        } catch (final IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        final SettingsController controller = domain.getSettingsController();

        for (final String className : groupClassNames) {
            if (controller.addSettingsGroup(className)) {
                logger.log(Level.INFO, "Loaded setting group: " + className);
            }
        }
    }
}
