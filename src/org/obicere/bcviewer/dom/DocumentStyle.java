package org.obicere.bcviewer.dom;

import java.awt.Color;

/**
 * @author Obicere
 */
public class DocumentStyle {

    private Color fontColor;

    private boolean fontColorValid = false;

    private Color highlightColor;

    private boolean highlightColorValid = false;

    private int fontStyle;

    private boolean fontStyleValid = false;

    private boolean underline;

    private boolean strikeThrough;

    public void setFontColor(final Color fontColor) {
        if (fontColor == null) {
            throw new NullPointerException("font color cannot be set to null.");
        }
        if (!this.fontColor.equals(fontColor)) {
            this.fontColor = fontColor;
            fontColorValid = false;
        }
    }

    public void setHighlightColor(final Color highlightColor) {
        if (this.highlightColor == highlightColor) {
            return;
        }
        if (highlightColor == null) {
            this.highlightColor = null;
            highlightColorValid = false;
            return;
        }
        if (!highlightColor.equals(this.highlightColor)) {
            this.highlightColor = highlightColor;
            highlightColorValid = false;
        }
    }

    public void setFontStyle(final int fontStyle) {
        if (this.fontStyle != fontStyle) {
            this.fontStyle = fontStyle;
            fontStyleValid = false;
        }
    }

    public void setUnderline(final boolean underline) {
        this.underline = underline;
    }

    public void setStrikeThrough(final boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

}
