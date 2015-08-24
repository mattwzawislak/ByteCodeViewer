package org.obicere.bcviewer.dom;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Obicere
 */
public class Attributes {

    private boolean underline;

    private boolean strikeThrough;

    private Script script;

    private Resource<Highlight> highlight;

    private Resource<Color> textColor;

    private Resource<Font> font;

    public Attributes(){

    }

    public Attributes(final Attributes attributes) {
        setAttributes(attributes);
    }

    public void setAttributes(final Attributes attributes){
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

    public Resource<Highlight> getHighlight() {
        return highlight;
    }

    public void setHighlight(final Resource<Highlight> highlight) {
        this.highlight = highlight;
    }

    public Resource<Color> getColor(){
        return textColor;
    }

    public void setColor(final Resource<Color> textColor) {
        this.textColor = textColor;
    }

    public Resource<Font> getFont(){
        return font;
    }

    public void setFont(final Resource<Font> font){
        this.font = font;
    }
}
