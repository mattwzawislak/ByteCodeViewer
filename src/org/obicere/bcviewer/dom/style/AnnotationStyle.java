package org.obicere.bcviewer.dom.style;

import org.obicere.bcviewer.dom.Style;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;

import java.awt.Color;
import java.awt.Font;

/**
 */
public class AnnotationStyle extends Style{

    // TODO set this based off of settings
    private static final Font FONT = new QuickWidthFont("Courier new", Font.PLAIN, 12);

    private static final Color COLOR = new Color(167, 185, 44);

    public AnnotationStyle() {
        super(FONT, COLOR);
    }
}
