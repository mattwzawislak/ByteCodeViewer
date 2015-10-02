package org.obicere.bcviewer.configuration;


import org.obicere.bcviewer.context.Domain;

import java.io.File;
import java.util.logging.Level;

/**
 * Contains the set of dependent paths the program will use. During the
 * startup process the folder-based paths will be created. Should they
 * fail, an error will be displayed in the console notifying which folder
 * failed to be created.
 * <p>
 * This also contains default site information. This way if the site
 * registration changes, this can be reflected application-wide.
 * <p>
 * The resource images are also stored here. Notably the
 * <code>close.png</code>, <code>complete.png</code>, and
 * <code>icon.png</code> image locations.
 * <p>
 * As of v1.0 no support has been added for modification-based custom
 * folders. This would have to be handled by the mod itself.
 *
 * @author Obicere
 * @version 1.0
 */
public class Paths {

    private final String RESOURCES_HOME  = "resource" + File.separator;
    private final String RESOURCES_ICONS = RESOURCES_HOME + "icons" + File.separator;

    private final String APP_DATA    = getAppData() + File.separator;
    private final String FOLDER_HOME = APP_DATA + "BytecodeViewer" + File.separator;

    private final String SETTINGS_FILE = FOLDER_HOME + "settings.txt";

    // List of system folders to create and the order in which they should be created.
    private final String[] PATHS = new String[]{APP_DATA, FOLDER_HOME};

    private final Domain domain;

    public Paths(final Domain domain) {
        this.domain = domain;

        makePaths();
    }

    /**
     * Attempts to create all the folders in specified in the system paths.
     * This list is not checked for order and {@link java.io.File#mkdirs()}
     * is not called, in the case that there are base folders that are
     * invalid. Should this happen, the user would most likely not want a
     * large mass of folders potentially being created in the wrong
     * directory.
     * <p>
     * In the case such an error happens - or another error for that
     * matter, the failed directory's location will be printed to the
     * console.
     */

    private void makePaths() {
        for (final String s : PATHS) {
            final File file = new File(s);
            if (!file.exists() && !file.mkdir()) {
                domain.getLogger().log(Level.WARNING, "Failed to create folder {0}.", file);
            }
        }
    }

    /**
     * Retrieves the default application storage folder for programs. This
     * is based off of the current operating system.
     * <p>
     * <pre>
     *     For Windows machines:    <code>%APPDATA%</code>
     *     For other machines:      <code>user.home</code>
     * </pre>
     * <p>
     * This has only been tested on Windows machines.
     *
     * @return The default application data folder.
     * @see org.obicere.bcviewer.configuration.OS#getOS()
     */

    public String getAppData() {
        return OS.getOS() == OS.WINDOWS ? System.getenv("APPDATA") : System.getProperty("user.home");
    }

    public String getResourceDirectory() {
        return RESOURCES_HOME;
    }

    public String getIconsDirectory() {
        return RESOURCES_ICONS;
    }

    public String getSettingsFile() {
        return SETTINGS_FILE;
    }

}
