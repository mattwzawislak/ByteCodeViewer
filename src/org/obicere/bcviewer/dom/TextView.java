package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * @author Obicere
 */
public class TextView extends View<TextElement> {

    public TextView(final TextElement element) {
        super(element);
    }

    @Override
    public void paintSelf(final Graphics g, final Rectangle bounds) {
        final String text = element.getText();
        final String trim = text.trim();
        final TextAttributes attributes = element.getAttributes();

        final Font font = attributes.getFont();
        final Script script = attributes.getScript();
        g.setFont(getFixedFont(font, script));

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
    protected Rectangle layoutSelf(final int x, final int y) {
        final Script script = element.getAttributes().getScript();
        final Font font = element.getAttributes().getFont();
        final Font fixedFont = getFixedFont(font, script);
        final FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);
        final Rectangle2D bounds = fixedFont.getStringBounds(element.getText(), fontRenderContext);
        final Rectangle integerBounds = bounds.getBounds();

        final int height;
        if (script == Script.BASELINE) {
            height = integerBounds.height;
        } else {
            height = (int) font.getStringBounds(element.getText(), fontRenderContext).getHeight();
        }

        return new Rectangle(x, y, integerBounds.width, height);
    }

    private Font getFixedFont(final Font font, final Script script) {
        if (script == Script.BASELINE) {
            return font;
        }
        return font.deriveFont(font.getSize() * script.getSize());
    }
}
