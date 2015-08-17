package org.obicere.bcviewer.dom;

import java.awt.Color;

/**
 * @author Obicere
 */
public class DocumentStyle {

    private Color fontColor;

    private Color highlightColor;

    private int fontStyle;

    private boolean underline;

    private boolean strikeThrough;

    public void setFontColor(final Color fontColor) {
        if (fontColor == null) {
            throw new NullPointerException("font color cannot be set to null.");
        }
        this.fontColor = fontColor;
    }

    public void setHighlightColor(final Color highlightColor) {
        if (this.highlightColor == highlightColor) {
            return;
        }
        this.highlightColor = highlightColor;
    }

    public void setFontStyle(final int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void setUnderline(final boolean underline) {
        this.underline = underline;
    }

    public void setStrikeThrough(final boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

}
