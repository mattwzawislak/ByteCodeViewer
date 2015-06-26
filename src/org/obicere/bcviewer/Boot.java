package org.obicere.bcviewer;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.FrameManager;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.utility.util.PrintFormatter;

import javax.swing.SwingUtilities;
import java.util.logging.ConsoleHandler;
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
        logger.info("Starting boot.");

        // Run the start up tasks
        performStartUp(logger);

        // Create the GUI
        SwingUtilities.invokeLater(() -> {
            final GUIManager manager = domain.getGUIManager();
            final FrameManager frameManager = manager.getFrameManager();
            frameManager.loadDefaultTheme();
            frameManager.open();
        });

        logger.info("Boot time took (ms): " + (System.currentTimeMillis() - start));
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
        domain.initialize();
    }

    private static void setUpLogger() {
        LogManager.getLogManager().reset();

        final PrintFormatter formatter = new PrintFormatter();

        // Console support
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getGlobal().addHandler(consoleHandler);
    }

    private static void performStartUp(final Logger logger) {
        final long start = System.currentTimeMillis();
        logger.fine("Starting StartUp");

        QUEUE.runTasks(logger);
        QUEUE.cleanUp();

        logger.fine("StartUp took (ms): " + (System.currentTimeMillis() - start));
    }

}
