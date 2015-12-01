package org.obicere.bytecode.viewer.dom.style;

import java.awt.Color;
import java.awt.Font;

/**
 */
public class Style {

    private Font font;

    private Color color;

    public Style() {
    }

    public Style(final Font font, final Color color) {
        this.font = font;
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public Color setColor(final Color color) {
        final Color old = this.color;
        this.color = color;
        return old;
    }

    public Font setFont(final Font font) {
        final Font old = this.font;
        this.font = font;
        return old;
    }
}
