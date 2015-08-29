package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Obicere
 */
public class TextView implements View {

    private final TextElement element;

    public TextView(final TextElement element) {
        this.element = element;
    }

    @Override
    public void paint(final Graphics g, final Rectangle bounds) {

        final String text = element.getText();
        final String trim = text.trim();
        final TextAttributes attributes = element.getAttributes();

        final Font font = attributes.getFont();
        if (font != null) {
            g.setFont(font);
        }

        final Script script = attributes.getScript();

        final float size = script.getSize();
        if (size != 1) {
            final Font current = g.getFont();
            final Font derived = current.deriveFont(current.getSize() * size);
            g.setFont(derived);
        }
        final FontMetrics metrics = g.getFontMetrics();
        final Rectangle trimBounds = metrics.getStringBounds(trim, g).getBounds();

        final Highlight highlight = attributes.getHighlight();
        if (highlight != null) {
            highlight.highlight(g, trimBounds);
        }

        final Color color = attributes.getColor();
        if (color != null) {
            g.setColor(color);
        }

        final int x = bounds.x;
        final int y = bounds.y + bounds.height - (int) ((metrics.getLeading() + metrics.getAscent()) * (script.getPosition()));
        g.drawString(text, x, y);

        // 0.08= 8% of the total height of the font should be the approximate
        // line size for the strike-through and underline attributes
        final double approximateLineSize = metrics.getHeight() * 0.08;
        final int lineSize = approximateLineSize > 1 ? (int) approximateLineSize : 1;
        if (attributes.isUnderline()) {
            // y + 1 -> the baseline of the text with a 1 pixel gap
            g.fillRect(bounds.x + trimBounds.x, y + 1, trimBounds.width, lineSize);
        }
        if (attributes.isStrikeThrough()) {
            // approximation for the median
            final int median = y - (int) (metrics.getHeight() * 0.33);
            g.fillRect(bounds.x + trimBounds.x, median, trimBounds.width, lineSize);
        }
    }

    @Override
    public Dimension getSize() {
        return null;
    }
}
