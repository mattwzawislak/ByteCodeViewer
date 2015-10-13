package org.obicere.bcviewer.util;

import org.obicere.bcviewer.dom.awt.QuickWidthFont;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class FontUtils {

    private static final ReentrantLock CACHE_LOCK = new ReentrantLock();

    private static volatile String[] FONT_NAMES;

    private static volatile String[] FIXED_WIDTH_FONT_NAMES;

    public static String[] getFontNames() {
        if (FONT_NAMES != null) {
            return FONT_NAMES;
        }
        try {
            CACHE_LOCK.lock();
            return getSafeFontNames();
        } finally {
            CACHE_LOCK.unlock();
        }
    }

    private static String[] getSafeFontNames() {
        if (FONT_NAMES != null) {
            return FONT_NAMES;
        }

        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        FONT_NAMES = environment.getAvailableFontFamilyNames();
        return FONT_NAMES;
    }

    public static String[] getFixedWidthFontNames() {
        if (FIXED_WIDTH_FONT_NAMES != null) {
            return FIXED_WIDTH_FONT_NAMES;
        }
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

}
