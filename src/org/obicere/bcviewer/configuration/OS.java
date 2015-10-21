package org.obicere.bcviewer.configuration;

import java.awt.dnd.DropTarget;

/**
 * The set of possible operating systems supported by the program.
 * There is a default value: {@link #OTHER}. Apart from that, the
 * values are related by name. The initial notion was in the case the
 * available operating system values need to be listed.
 * <p>
 * Pretty straight forward. These really could be replaced by just
 * constant <code>int</code> values as opposed to an
 * <code>enum</code>.
 */

public enum OS {

    WINDOWS, MAC, LINUX, OTHER;

    private static final OS SYSTEM_OS;

    static {

        SYSTEM_OS = parseOS();
    }

    /**
     * Returns the value of the parsed operating system.
     *
     * @return The parsed OS value.
     * @see #parseOS()
     */

    public static OS getOS() {
        return SYSTEM_OS;
    }

    /**
     * Attempts to parse the operating system from the system properties.
     * Namely the <code>os.name</code> property. If one cannot be found, it
     * will return the {@link OS#OTHER} value. Otherwise it will return the
     * appropriate value - I hope - in accordance to the operating system.
     * <p>
     * If the <code>os.name</code> contains ignoring case:
     * <pre>
     *     "windows";   returns {@link OS#WINDOWS}
     *     "mac":       returns {@link OS#MAC}
     *     "linux":     returns {@link OS#LINUX}
     *     default:     returns {@link OS#OTHER}
     * </pre>
     * <p>
     * Should there be any error, help would be great getting the right
     * values.
     *
     * @return Hopefully the correct operating system value.
     */

    private static OS parseOS() {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            return OS.WINDOWS;
        }
        if (os.contains("mac")) {
            return OS.MAC;
        }
        if (os.contains("linux")) {
            return OS.LINUX;
        }
        return OS.OTHER;
    }
}
