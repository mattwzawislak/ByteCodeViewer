package org.obicere.bytecode.viewer.dom.style;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class StyleConstants {

    public static final Style PLAIN      = new Style();
    public static final Style ANNOTATION = new Style();
    public static final Style COMMENT    = new Style();
    public static final Style KEYWORD    = new Style();
    public static final Style NUMBER     = new Style();
    public static final Style STRING     = new Style();
    public static final Style TYPE       = new Style();

    private static final ReentrantLock STYLE_LOCK = new ReentrantLock();

    public static void setStyleColor(final Style style, final Color color) {
        if (color == null) {
            throw new NullPointerException("color must ne non-null.");
        }
        if (style == null) {
            throw new NullPointerException("style must be non-null.");
        }
        try {
            STYLE_LOCK.lock();
            style.setColor(color);
        } finally {
            STYLE_LOCK.unlock();
        }
    }

    public static void setFontStyle(final Style style, final Font font) {
        if (font == null) {
            throw new NullPointerException("font must ne non-null.");
        }
        if (style == null) {
            throw new NullPointerException("style must be non-null.");
        }
        try {
            STYLE_LOCK.lock();
            style.setFont(font);
        } finally {
            STYLE_LOCK.unlock();
        }
    }

    public static <T> T submit(final Callable<T> runnable) {
        try {
            STYLE_LOCK.lock();
            return runnable.call();
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            STYLE_LOCK.unlock();
        }
    }
}
