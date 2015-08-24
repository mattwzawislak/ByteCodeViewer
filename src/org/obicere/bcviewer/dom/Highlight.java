package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * @author Obicere
 */
public class Highlight {

    private final Color color;

    public Highlight(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void highlight(final Graphics g, final Shape bounds) {
        if (color == null) {
            return;
        }
        final Rectangle rect = bounds.getBounds();
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

    }

}
