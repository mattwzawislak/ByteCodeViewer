package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Obicere
 */
public class Attributes {

    private boolean underline;

    private boolean strikeThrough;

    private Script script = Script.BASELINE;

    private Color textColor;

    private Color highlightColor = new Color(0, 64, 128, 200);

    private Font font;

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(final boolean underline) {
        this.underline = underline;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(final boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(final Script script) {
        this.script = script;
    }

    public Color getColor() {
        return textColor;
    }

    public void setColor(final Color textColor) {
        this.textColor = textColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(final Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(final Font font) {
        this.font = font;
    }
}
