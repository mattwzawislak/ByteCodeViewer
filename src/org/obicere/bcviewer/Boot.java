package org.obicere.bcviewer;

import org.obicere.bcviewer.configuration.ClassFileLoader;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.bcviewer.startup.StartUpQueue;
import org.obicere.bcviewer.startup.StartUpTaskLoader;
import org.obicere.utility.util.PrintFormatter;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class Boot {

    private static Domain domain;

    private static final StartUpQueue QUEUE = new StartUpQueue();

    public static void main(final String[] args) {
        // Prepare the current boot
        final long start = System.currentTimeMillis();
        prepareBoot();

        // Starting the actual boot sequence, everything needed for boot
        // should be loaded at this point
        final Logger logger = domain.getLogger();
        logger.info(domain.getApplicationName());
        logger.info("Starting boot.");

        // Run the start up tasks. This ideally will only set the
        // appropriate values to the appropriate places for the
        // initialization to run and set everything properly.
        performStartUp(domain);

        // Once start up is complete we can initialize everything we need
        // except for the domain - which should be initialized already as
        // it is a fundamental process.
        // This is where settings should be applied, profiles should be
        // loaded.
        initializeSystem(domain);

        // Create the GUI
        SwingUtilities.invokeLater(() -> {
            final GUIManager manager = domain.getGUIManager();
            final FrameManager frameManager = manager.getFrameManager();
            frameManager.open();
        });

        logger.info("Boot time took (ms): " + (System.currentTimeMillis() - start));

        openFiles(args);
    }

    public static Domain getGlobalDomain() {
        return domain;
    }

    public static StartUpQueue getStartUpQueue() {
        return QUEUE;
    }

    private static void prepareBoot() {

        setUpLogger();
        domain = new Domain();
    }

    private static void setUpLogger() {
        LogManager.getLogManager().reset();

        final PrintFormatter formatter = new PrintFormatter();

        // Console support
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getGlobal().addHandler(consoleHandler);
    }

    private static void performStartUp(final Domain domain) {
        final long start = System.currentTimeMillis();
        domain.getLogger().fine("Starting StartUp");

        final StartUpTaskLoader loader = new StartUpTaskLoader();
        loader.loadStartUpTasks();
        QUEUE.runTasks(domain);

        domain.getLogger().fine("StartUp took (ms): " + (System.currentTimeMillis() - start));
    }

    private static void initializeSystem(final Domain domain) {

        domain.getSettingsController().loadSettings();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> domain.getSettingsController().saveSettings()));
    }

    private static void openFiles(final String[] args) {

        final ClassFileLoader loader = domain.getClassLoader();
        final Logger logger = domain.getLogger();

        for (int i = 0; i < args.length; i++) {
            final String argument = args[i];
            if (argument == null || argument.length() == 0) {
                logger.log(Level.CONFIG, "Skipped file argument: " + (i + 1) + " as it was empty.");
                continue;
            }
            final File file = new File(argument);
            if (!file.exists()) {
                logger.log(Level.CONFIG, "File argument: \"" + argument + "\" does not exist.");
                continue;
            }

            if (!file.canRead()) {
                logger.log(Level.CONFIG, "File argument: \"" + argument + "\" cannot be read from.");
                continue;
            }

            loader.load(file);
        }
    }
}
