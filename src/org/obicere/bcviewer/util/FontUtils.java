package org.obicere.bcviewer.util;

import org.obicere.bcviewer.dom.awt.QuickWidthFont;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.LinkedList;

/**
 */
public class FontUtils {

    public static String[] getFontNames() {
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return environment.getAvailableFontFamilyNames();
    }

    public static String[] getFixedWidthFontNames() {
        final String[] fontNames = getFontNames();
        final LinkedList<String> fixedWidthNames = new LinkedList<>();

        for (final String fontName : fontNames) {
            final QuickWidthFont font = new QuickWidthFont(fontName, Font.PLAIN, 14);
            if (font.isFixedWidth()) {
                fixedWidthNames.add(fontName);
            }
        }

        return fixedWidthNames.toArray(new String[fixedWidthNames.size()]);
    }

}
