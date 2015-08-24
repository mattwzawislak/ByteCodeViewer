package org.obicere.bcviewer.dom;

import java.awt.Color;

/**
 * @author Obicere
 */
public class Attributes {

    private boolean underline;

    private boolean strikeThrough;

    private Script script;

    private Highlight highlight;

    private Color textColor;

    public Attributes(final Attributes attributes){
        this.underline = attributes.underline;
        this.strikeThrough = attributes.strikeThrough;
        this.script = attributes.script;
        this.highlight = attributes.highlight;
        this.textColor = attributes.textColor;
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

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(final Color textColor) {
        this.textColor = textColor;
    }
}
