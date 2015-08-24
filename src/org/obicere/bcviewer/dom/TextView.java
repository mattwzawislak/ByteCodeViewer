package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

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

        final Resource<Font> fontResource = attributes.getFont();
        if (fontResource != null) {
            final Font font = fontResource.get();
            g.setFont(font);
        }
        final FontMetrics metrics = g.getFontMetrics();
        final Rectangle trimBounds = metrics.getStringBounds(trim, g).getBounds();

        final Resource<Highlight> highlightResource = attributes.getHighlight();
        if (highlightResource != null) {
            final Highlight highlight = highlightResource.get();
            highlight.highlight(g, trimBounds);
        }

        final Resource<Color> colorResource = attributes.getColor();
        if (colorResource != null) {
            final Color color = colorResource.get();
            g.setColor(color);
        }

        switch (attributes.getScript()) {
            case SUBSCRIPT:
                drawStringSubscript(g, text, bounds, metrics);
                break;
            case SUPERSCRIPT:
                drawStringSuperscript(g, text, bounds, metrics);
                break;
            case BASELINE:
                drawStringBaseline(g, text, bounds, metrics);
                break;
        }

        if (attributes.isUnderline()) {
            final int baseline = bounds.y + metrics.getLeading() + metrics.getAscent();
            g.drawLine(bounds.x + trimBounds.x, baseline, bounds.x + trimBounds.x + trimBounds.width, baseline);
        }
        if (attributes.isStrikeThrough()) {
            // approximation for the baseline
            final int median = bounds.y + metrics.getLeading() + (int) (metrics.getAscent() * 0.66);
            g.drawLine(bounds.x + trimBounds.x, median, bounds.x + trimBounds.x + trimBounds.width, median);
        }
    }

    private void drawStringSubscript(final Graphics g, final String text, final Rectangle bounds, final FontMetrics metrics) {
        final Font font = g.getFont();
        final Font small = font.deriveFont(font.getSize() * 0.66f);
        g.setFont(small);

        final int baseline = bounds.y + bounds.height - metrics.getDescent();
        g.drawString(text, bounds.x, baseline);
    }

    private void drawStringSuperscript(final Graphics g, final String text, final Rectangle bounds, final FontMetrics metrics) {
        final Font font = g.getFont();
        final Font small = font.deriveFont(font.getSize() * 0.66f);
        g.setFont(small);

        final Rectangle2D textBounds = metrics.getStringBounds(text, g);
        final double baselineOffset = textBounds.getHeight() * 0.66;
        final int baseline = bounds.y + bounds.height - (int) baselineOffset;
        g.drawString(text, bounds.x, baseline);
    }

    private void drawStringBaseline(final Graphics g, final String text, final Rectangle bounds, final FontMetrics metrics) {
        final int baseline = bounds.y + bounds.height - (int) (metrics.getAscent() * 0.34);
        g.drawString(text, bounds.x, baseline);
    }
}
