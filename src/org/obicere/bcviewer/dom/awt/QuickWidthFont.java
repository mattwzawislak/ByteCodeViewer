package org.obicere.bcviewer.dom.awt;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.util.Map;

/**
 */
public class QuickWidthFont extends Font {

    private final boolean isFixedWidth;

    private int width;
    private int height;

    public QuickWidthFont(final String name, final int style, final int size) {
        super(name, style, size);
        this.isFixedWidth = checkFixedWidth();
    }

    public QuickWidthFont(final Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) {
        super(attributes);
        this.isFixedWidth = checkFixedWidth();
    }

    protected QuickWidthFont(final Font font) {
        super(font);
        this.isFixedWidth = checkFixedWidth();
    }

    private boolean checkFixedWidth() {

        // this is a shitty test to be honest
        // but it works!
        final char thinText = 'i';
        final char thickText = 'w';

        if (!canDisplay(thinText) || !canDisplay(thickText)) {
            // should be able to display ascii
            return false;
        }

        final Rectangle2D thinRect = super.getStringBounds(new char[]{thinText}, 0, 1, new FontRenderContext(null, true, true));
        final Rectangle2D thickRect = super.getStringBounds(new char[]{thickText}, 0, 1, new FontRenderContext(null, true, true));

        if (thinRect.getWidth() == thickRect.getWidth()) {

            width = (int) Math.round(thickRect.getWidth());
            height = (int) Math.round(thickRect.getHeight());
            return true;
        } else {
            return false;
        }
    }

    public boolean isFixedWidth() {
        return isFixedWidth;
    }

    public int getFixedWidth() {
        return width;
    }

    public int getFixedHeight() {
        return height;
    }

    @Override
    public Rectangle2D getStringBounds(final String str, final FontRenderContext frc) {
        if (isFixedWidth) {
            return getStringBounds(str.length());
        }
        return super.getStringBounds(str, frc);
    }

    @Override
    public Rectangle2D getStringBounds(final String str, final int beginIndex, final int limit, final FontRenderContext frc) {
        if (isFixedWidth) {
            return getStringBounds(limit - beginIndex);
        }
        return super.getStringBounds(str, beginIndex, limit, frc);
    }

    @Override
    public Rectangle2D getStringBounds(final char[] chars, final int beginIndex, final int limit, final FontRenderContext frc) {
        if (isFixedWidth) {
            return getStringBounds(limit - beginIndex);
        }
        return super.getStringBounds(chars, beginIndex, limit, frc);
    }

    @Override
    public Rectangle2D getStringBounds(final CharacterIterator ci, final int beginIndex, final int limit, final FontRenderContext frc) {
        if (isFixedWidth) {
            return getStringBounds(ci.getEndIndex() - ci.getBeginIndex());
        }
        return super.getStringBounds(ci, beginIndex, limit, frc);
    }

    public Rectangle getStringBounds(final int count) {
        return new Rectangle(0, 0, width * count, height);
    }
}
