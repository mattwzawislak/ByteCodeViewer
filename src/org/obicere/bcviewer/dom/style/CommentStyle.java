package org.obicere.bcviewer.dom.style;

import org.obicere.bcviewer.dom.Style;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;

import java.awt.Color;
import java.awt.Font;

/**
 */
public class CommentStyle extends Style {

    // TODO set this based off of settings
    private static final Font FONT = new QuickWidthFont("Courier new", Font.ITALIC, 12);

    private static final Color COLOR = new Color(188, 188, 188);

    public CommentStyle() {
        super(FONT, COLOR);
    }
}
