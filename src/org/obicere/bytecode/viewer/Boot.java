package org.obicere.bytecode.viewer;

import org.obicere.bytecode.viewer.configuration.ClassFileLoader;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.FrameManager;
import org.obicere.bytecode.viewer.gui.GUIManager;
import org.obicere.bytecode.viewer.settings.SettingsController;
import org.obicere.bytecode.viewer.startup.StartUpQueue;
import org.obicere.bytecode.viewer.startup.StartUpTaskLoader;
import org.obicere.bytecode.viewer.util.PrintFormatter;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The main entry point of the ByteCode Viewer. This is responsible for
 * setting up the domain of the application, the logging handlers and
 * settings loading. <p> Any arguments passed in must follow the given
 * format:
 * <ul>
 * <li> <code>&lt;filename&gt;</code>
 * <p>Where the <code>filename</code> is the location of a class file,
 * archive, jar, or any other form of file this program is capable of
 * opening. The most up-to-date and complete list will be found in the
 * {@link org.obicere.bytecode.viewer.configuration.ClassFileLoader}
 * documentation as well as any known errors.
 * </p>
 * <p>
 * Files that have spaces in the name should be wrapped with double
 * quotes. Such as <code>/user/main user/Test.class</code> should be
 * <code>"/user/main user/Test.class"</code>.
 * </p>
 * </ul>
 * The order of operations is:
 * <ul>
 * <li> Setting up logger and domain
 * <li> Running startup tasks
 * <li> Settings are loaded
 * <li> Window is initialized and started
 * <li> All files passed in as arguments are loaded
 * </ul>
 *
 * @author Obicere
 * @version 0.0
 * @see org.obicere.bytecode.viewer.context.Domain
 * @since 0.0
 */
public class Boot {

    /**
     * The global domain for the application. Ideally, this would be a
     * local field and only accessible through objects that provide {@link
     * org.obicere.bytecode.viewer.context.DomainAccess}.
     */

    private static Domain domain;

    /**
     * The start up queue for any tasks that need to be ran at boot after
     * the initialization of the domain.
     */

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

        // Handle the arguments. This will need to be modified if the
        // argument handling changes.
        openFiles(args);
    }

    /**
     * Return the domain for the application. This is not the preferred
     * way
     * to retrieve the domain. The only reason this should be used is if
     * the domain is otherwise inaccessible from an object that provides
     * access.
     * <p>
     * This should only be called after domain initialization. Otherwise
     * the domain will not have been initialized yet. Ideally, this would
     * be a local field and only accessible through objects that provide
     * {@link org.obicere.bytecode.viewer.context.DomainAccess}.
     *
     * @return The domain of the program. If <code>null</code>, then the
     * access of the domain is premature and the domain does not exist
     * yet.
     */
    public static Domain getGlobalDomain() {
        return domain;
    }

    /**
     * Provides access to the start up queue so tasks can be submitted
     * from
     * in-house or 3rd-party modification programs. Any tasks submitted to
     * here are considered optional and should not be required for the
     * application to run properly. These tasks will run after the
     * initialization of the domain.
     *
     * @return The start up queue for tasks to be ran off of.
     */

    public static StartUpQueue getStartUpQueue() {
        return QUEUE;
    }

    /**
     * Prepares the boot by initializing the domain and setting up the
     * logging system. Everything that requires the domain must be done
     * after this call. Any attempt to request the global domain must also
     * be done after this call.
     */

    private static void prepareBoot() {

        setUpLogger();
        domain = new Domain();
    }

    /**
     * Sets up the logger. This currently supports console support using
     * the {@link org.obicere.bytecode.viewer.util.PrintFormatter} format:
     * <p>
     * <code>Month/Day/Year Hour:Minutes:Seconds <i>level</i>:
     * <i>message</i></code>
     * <p>
     * This will clear all changes to the log managers. Therefore if a
     * custom logging system is in place, it <i>must</i> be done after
     * this
     * system is in place.
     */

    private static void setUpLogger() {

        LogManager.getLogManager().reset();

        final PrintFormatter formatter = new PrintFormatter();

        // Console support
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(formatter);

        Logger.getGlobal().addHandler(consoleHandler);
    }

    /**
     * Runs the start up tasks as well as logging the time the tasks took.
     * The print out for the duration requires <i>fine</i> logging.
     * <p>
     * As of this call, the domain and the logging will be initialized.
     * <p>
     * As of v0.0 this will only handle application default tasks.
     *
     * @param domain The domain of the application. This must be
     *               non-<code>null</code> and must be initialized for
     *               usage in the tasks.
     */
    private static void performStartUp(final Domain domain) {
        final long start = System.currentTimeMillis();
        domain.getLogger().fine("Starting StartUp");

        final StartUpTaskLoader loader = new StartUpTaskLoader();
        loader.loadStartUpTasks();
        QUEUE.runTasks(domain);

        domain.getLogger().fine("StartUp took (ms): " + (System.currentTimeMillis() - start));
    }

    /**
     * Initializes the system. This performs all the settings operations
     * including loading and saving. A runtime shutdown hook is added to
     * save settings - assuming a non-fatal crash.
     * <p>
     * As of this call, the domain and logging will be initialized. The
     * start up tasks will be performed.
     *
     * @param domain The domain of the application. This must be
     *               non-<code>null</code> as the domain contains the
     *               settings.
     */
    private static void initializeSystem(final Domain domain) {
        final SettingsController controller = domain.getSettingsController();

        controller.loadSettings();

        final Runnable settingsShutDown = (controller::saveSettings);
        Runtime.getRuntime().addShutdownHook(new Thread(settingsShutDown));
    }

    /**
     * Processes the arguments as input files. This is the only processing
     * that is done on the arguments so far. Should a file fail to be
     * loaded, an appropriate message explaining the reason will
     * printed to the console using a <code>warning</code> level.
     * <p>
     * Files passed in that have spaces in the names should be wrapped in
     * double quotation marks. For example:
     * <p>
     * <code>/user/main user/Test.class</code>
     * <p>
     * Should be passed in as:
     * <p>
     * <code>"/user/main user/Test.class"</code>
     *
     * @param args The arguments to load as files.
     */

    private static void openFiles(final String[] args) {

        final ClassFileLoader loader = domain.getClassLoader();
        final Logger logger = domain.getLogger();

        for (int i = 0; i < args.length; i++) {
            final String argument = args[i];

            // file can't be invalid
            if (argument == null || argument.length() == 0) {
                logger.log(Level.WARNING, "Skipped file argument: " + (i + 1) + " as it was empty.");
                continue;
            }
            final File file = new File(argument);

            // check to see if the file exists
            if (!file.exists()) {
                logger.log(Level.WARNING, "File argument: \"" + argument + "\" does not exist.");
                continue;
            }

            // check to see if the file can read
            if (!file.canRead()) {
                logger.log(Level.WARNING, "File argument: \"" + argument + "\" cannot be read from.");
                continue;
            }

            loader.load(file);
        }
    }
}
