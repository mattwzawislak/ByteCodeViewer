package org.obicere.bcviewer.util;

import org.obicere.bcviewer.dom.awt.QuickWidthFont;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Utility for loading and caching available font names. This includes
 * processing for fixed-width fonts to allow faster sizing options in text
 * based components.
 * <p>
 * This loads all fonts from the local graphics environment - accessible
 * through
 * {@link java.awt.GraphicsEnvironment#getLocalGraphicsEnvironment()}.
 * <p>
 * The size of the cache could be quite large, seeing as all fonts loaded
 * onto the system are stored by name. Should the font names be no longer
 * needed or maintainable, the cache can be released. This will free the
 * memory from static memory.
 * <p>
 * Due to memory constraints already present with the large list of
 * available font names, it should not be assumed the return fonts are
 * unmodifiable. Changes can be made to the list of fonts; however, no
 * contracts are broken by allowing such changes. Therefore for the sake
 * of performance and memory, no cloning is required.
 *
 * @see org.obicere.bcviewer.dom.awt.QuickWidthFont
 */
public class FontUtils {

    /**
     * Synchronizes access to the
     * {@link org.obicere.bcviewer.util.FontUtils#FONT_NAMES} and
     * {@link org.obicere.bcviewer.util.FontUtils#FIXED_WIDTH_FONT_NAMES}
     * so as to not run into multi-threading issues.
     */
    private static final ReentrantLock CACHE_LOCK = new ReentrantLock();

    /**
     * The cache for the list of all font family names from the local
     * graphics environment.
     *
     * @see org.obicere.bcviewer.util.FontUtils#getFontNames()
     */
    private static volatile String[] FONT_NAMES;

    /**
     * The cache for the list of all fixed width font family names from
     * the local graphics environment. There are fonts that under the
     * {@link org.obicere.bcviewer.dom.awt.QuickWidthFont} are tested
     * successfully as fixed-width (monospaced) fonts.
     *
     * @see org.obicere.bcviewer.util.FontUtils#getFixedWidthFontNames() ()
     */
    private static volatile String[] FIXED_WIDTH_FONT_NAMES;

    /**
     * Retrieves the list of all font names. These values are cached and
     * can be released by calling
     * {@link org.obicere.bcviewer.util.FontUtils#releaseCache()}.
     *
     * @return The list of all fonts names accessible on the system.
     * @see org.obicere.bcviewer.util.FontUtils#getSafeFontNames()
     */

    public static String[] getFontNames() {
        try {
            CACHE_LOCK.lock();

            if (FONT_NAMES != null) {
                return FONT_NAMES;
            }
            final String[] fontNames = getSafeFontNames();

            FONT_NAMES = fontNames;

            return fontNames;
        } finally {
            CACHE_LOCK.unlock();
        }
    }

    /**
     * Get the list of all family font names on the system. This should
     * only every be ran under synchronization to avoid redundantly making
     * this call and recreating the cache unnecessarily.
     *
     * @return The list of font names on the system.
     */
    private static String[] getSafeFontNames() {
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return environment.getAvailableFontFamilyNames();
    }

    /**
     * Retrieves the list of all fixed-width font names. These values are
     * cached and can be released by calling
     * {@link org.obicere.bcviewer.util.FontUtils#releaseCache()}.
     * <p>
     * The fonts are tested using the {@link java.awt.Font#PLAIN} with a
     * size <code>14</code> font. These values were picked based off of
     * what an expected font would be. This has not been extensively
     * tested.
     *
     * @return The list of all fonts names accessible on the system.
     * @see org.obicere.bcviewer.util.FontUtils#getSafeFontNames()
     */

    public static String[] getFixedWidthFontNames() {
        try {
            CACHE_LOCK.lock();
            if (FIXED_WIDTH_FONT_NAMES != null) {
                return FIXED_WIDTH_FONT_NAMES;
            }

            final String[] fontNames = getSafeFontNames();
            final LinkedList<String> fixedWidthNames = new LinkedList<>();

            for (final String fontName : fontNames) {
                final QuickWidthFont font = new QuickWidthFont(fontName, Font.PLAIN, 14);
                if (font.isFixedWidth()) {
                    fixedWidthNames.add(fontName);
                }
            }

            FIXED_WIDTH_FONT_NAMES = fixedWidthNames.toArray(new String[fixedWidthNames.size()]);
            return FIXED_WIDTH_FONT_NAMES;
        } finally {
            CACHE_LOCK.unlock();
        }
    }

    /**
     * Releases the cached font names. This can also be used to update the
     * list of loaded fonts should this change since the last cache. This
     * will clear both of the family font names and fixed-width family
     * font names.
     */
    public static void releaseCache() {
        try {
            CACHE_LOCK.lock();
            FONT_NAMES = null;
            FIXED_WIDTH_FONT_NAMES = null;
        } finally {
            CACHE_LOCK.unlock();
        }
    }
}
