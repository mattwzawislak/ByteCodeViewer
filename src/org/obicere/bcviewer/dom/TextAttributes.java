package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Obicere
 */
public class TextAttributes {

    private boolean underline;

    private boolean strikeThrough;

    private Script script = Script.BASELINE;

    private Highlight highlight;

    private Color textColor;

    private Font font;

    public TextAttributes() {

    }

    public TextAttributes(final TextAttributes attributes) {
        setAttributes(attributes);
    }

    public void setAttributes(final TextAttributes attributes) {
        this.underline = attributes.underline;
        this.strikeThrough = attributes.strikeThrough;
        this.script = attributes.script;
        this.highlight = attributes.highlight;
        this.textColor = attributes.textColor;
        this.font = attributes.font;
    }

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

    public Highlight getHighlight() {
        return highlight;
    }

    public void setHighlight(final Highlight highlight) {
        this.highlight = highlight;
    }

    public Color getColor() {
        return textColor;
    }

    public void setColor(final Color textColor) {
        this.textColor = textColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(final Font font) {
        this.font = font;
    }
}
