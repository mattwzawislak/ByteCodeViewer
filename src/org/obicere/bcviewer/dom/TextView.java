package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.Attribute;

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

    private static final PaddingCache CACHE = PaddingCache.getPaddingCache();

    private final Rectangle textBounds = new Rectangle();
    private final Rectangle self       = new Rectangle();

    private volatile boolean cached = false;

    public TextView(final TextElement element) {
        super(element);
    }

    public void dispose() {
        cached = false;
    }

    public Rectangle getTextBounds() {
        if (!isArranged()) {
            return EMPTY_RECTANGLE;
        }
        return textBounds;
    }

    @Override
    public void paintSelf(final Graphics g, final Rectangle bounds) {
        final String text = element.getDisplayText();
        final String trim = text.trim();
        final Attributes attributes = element.getAttributes();

        final Script script = attributes.getScript();
        final Font fixedFont = getFixedFont();

        g.setFont(fixedFont);

        final FontMetrics metrics = g.getFontMetrics();
        final Rectangle trimBounds = metrics.getStringBounds(trim, g).getBounds();


        final int x = bounds.x;
        final int y = bounds.y + bounds.height - (int) ((metrics.getLeading() + metrics.getAscent()) * (script.getPosition()));

        if (element.isHighlighted()) {
            if (element.isHighlightClipped()) {
                drawTextHighlightSplice(g, metrics, attributes, x, y);
            } else {
                drawTextHighlightNoSplice(g, metrics, attributes, x, y);
            }
        } else {
            drawTextNoHighlight(g, attributes, x, y);
        }

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

    private void drawTextNoHighlight(final Graphics g, final Attributes attributes, final int x, final int y) {
        final Color color = attributes.getColor();
        if (color != null) {
            g.setColor(color);
        }
        g.drawString(element.getDisplayText(), x, y);
    }

    private void drawTextHighlightNoSplice(final Graphics g, final FontMetrics metrics, final Attributes attributes, final int x, final int y) {

        final String display = element.getDisplayText();

        final int leadWidth;
        if (element.getHighlightStart() == 0) {
            leadWidth = 0;
        } else {
            final Rectangle lead = metrics.getStringBounds(CACHE.getPadding(element.getHighlightStart()), g).getBounds();
            leadWidth = lead.width;
        }
        final Rectangle displayArea = metrics.getStringBounds(display, element.getHighlightStart(), element.getHighlightEnd(), g).getBounds();

        displayArea.x = leadWidth + x;
        displayArea.y = y - displayArea.height + metrics.getDescent();
        //displayArea.height += metrics.getAscent();

        g.setColor(attributes.getHighlightColor());
        g.fillRect(displayArea.x, displayArea.y, displayArea.width, displayArea.height);

        g.setColor(Color.WHITE);
        g.drawString(display, x, y);
    }

    private void drawTextHighlightSplice(final Graphics g, final FontMetrics metrics, final Attributes attributes, final int x, final int y) {
        final String display = element.getDisplayText();
        final String leadText = display.substring(0, element.getHighlightStart());
        final String centerText = display.substring(element.getHighlightStart(), element.getHighlightEnd());
        final String trailingText = display.substring(element.getHighlightEnd());

        final int leadWidth;
        if (element.getHighlightStart() == 0) {
            leadWidth = 0;
        } else {
            final Rectangle lead = metrics.getStringBounds(leadText, g).getBounds();
            leadWidth = lead.width;
        }

        final Rectangle centerArea = metrics.getStringBounds(display, element.getHighlightStart(), element.getHighlightEnd(), g).getBounds();

        final Color color = attributes.getColor();
        if (color != null) {
            g.setColor(color);
        }
        g.drawString(leadText, x, y);
        g.drawString(trailingText, x + leadWidth + centerArea.width, y);

        centerArea.x = leadWidth + x;
        centerArea.y = y - centerArea.height + metrics.getDescent();

        g.setColor(attributes.getHighlightColor());
        g.fillRect(centerArea.x, centerArea.y, centerArea.width, centerArea.height);

        g.setColor(Color.WHITE);
        g.drawString(centerText, x + leadWidth, y);


    }

    @Override
    protected Rectangle layoutSelf(final int x, final int y) {
        // if cached, we can boycott this constant calculation completely
        if (!cached) {
            final Script script = element.getAttributes().getScript();
            final Font font = element.getAttributes().getFont();
            final Font fixedFont = getFixedFont();
            final FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);

            final String leftPad = CACHE.getPadding(element.getLeftPad());
            final String rightPad = CACHE.getPadding(element.getRightPad());
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

            textBounds.x = x + leftPadBounds.width;
            textBounds.y = y;
            //textBounds.width = textBounds.width;
            textBounds.height = height;

            self.width = leftPadBounds.width + textBounds.width + rightPadBounds.width;
            self.height = height;

            cached = true;
        }
        self.x = x;
        self.y = y;
        return self;
    }

    @Override
    protected Rectangle layoutChildren(final Rectangle parent) {
        final Rectangle clone = new Rectangle(parent);
        final FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);
        final String fullLeftPad = CACHE.getPadding(element.getLeftPad());

        final int padOffset = (int) getFixedFont().getStringBounds(fullLeftPad, fontRenderContext).getWidth();
        clone.x += padOffset;

        final Rectangle bounds = super.layoutChildren(clone);
        bounds.width += padOffset;
        return bounds;
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
