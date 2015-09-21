package org.obicere.bcviewer.dom;

import java.awt.Color;

/**
 */
public class ColorResourcePool extends ResourcePool<Color> {

    public static final String COLOR_PLAIN = "plain";

    public static final String COLOR_KEYWORD = "keyword";

    public static final String COLOR_NUMBER = "number";

    public static final String COLOR_STRING = "string";

    public static final String COLOR_COMMENT = "comment";

    private final DocumentBuilder builder;

    public ColorResourcePool(final DocumentBuilder builder) {
        this.builder = builder;
        // TODO Move to settings
        safeAdd(COLOR_PLAIN, new Color(16, 16, 16));
        safeAdd(COLOR_KEYWORD, new Color(227, 80, 0));
        safeAdd(COLOR_NUMBER, new Color(86, 151, 250));
        safeAdd(COLOR_STRING, new Color(0, 128, 52));
        safeAdd(COLOR_COMMENT, new Color(188, 188, 188));
    }

    @Override
    public void add(final String name, final Color color) {
        super.add(name, color);
        builder.notifyColorChange();
    }


}
