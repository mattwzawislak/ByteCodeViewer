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

    private Rectangle textBounds;

    public TextView(final TextElement element) {
        super(element);
    }

    public Rectangle getTextBounds() {
        if (!isArranged()) {
            return new Rectangle();
        }
        return textBounds;
    }

    @Override
    public void paintSelf(final Graphics g, final Rectangle bounds) {
        final String text = element.getDisplayText();
        final String trim = text.trim();
        final TextAttributes attributes = element.getAttributes();

        final Script script = attributes.getScript();
        final Font fixedFont = getFixedFont();

        g.setFont(fixedFont);

        final FontMetrics metrics = g.getFontMetrics();
        final Rectangle trimBounds = metrics.getStringBounds(trim, g).getBounds();

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
        final Font fixedFont = getFixedFont();
        final FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);

        final PaddingCache cache = new PaddingCache();
        final String leftPad = cache.getPadding(element.getCumulativeLeftPad());
        final String rightPad = cache.getPadding(element.getCumulativeRightPad());
        final String text = element.getText();

        final Rectangle leftPadBounds = fixedFont.getStringBounds(leftPad, fontRenderContext).getBounds();
        final Rectangle rightPadBounds = fixedFont.getStringBounds(rightPad, fontRenderContext).getBounds();
        final Rectangle textBounds = fixedFont.getStringBounds(text, fontRenderContext).getBounds();

        final int height;
        if (script == Script.BASELINE) {
            height = textBounds.height;
        } else {
            height = (int) font.getStringBounds(element.getText(), fontRenderContext).getHeight();
        }

        this.textBounds = new Rectangle(x + leftPadBounds.width, y, textBounds.width, height);

        return new Rectangle(x, y, leftPadBounds.width + textBounds.width + rightPadBounds.width, height);
    }

    private Font getFixedFont() {
        final Font font = element.getAttributes().getFont();
        final Script script = element.getAttributes().getScript();
        if (script == Script.BASELINE) {
            return font;
        }
        return font.deriveFont(font.getSize() * script.getSize());
    }

    public int getCaretIndex(final int x, final int y) {
        if (!getSize().contains(x, y)) {
            return -1;
        }
        final int overflowWidth = x - getSize().x;
        final Font fixedFont = getFixedFont();
        final FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);
        final char[] chars = element.getDisplayText().toCharArray();
        for (int i = 1; i < chars.length; i++) {
            final Rectangle2D metrics = fixedFont.getStringBounds(chars, 0, i, fontRenderContext);
            if (metrics.getWidth() > overflowWidth) {
                return i - 1;
            }
        }
        return -1;
    }

    public int getCaretLocation(final int index) {
        final Font fixedFont = getFixedFont();
        final FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);
        final Rectangle2D metrics = fixedFont.getStringBounds(element.getDisplayText(), 0, index, fontRenderContext);
        return (int) metrics.getWidth();
    }
}
