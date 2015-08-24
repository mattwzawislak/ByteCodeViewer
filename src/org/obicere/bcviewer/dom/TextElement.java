package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class TextElement extends Element {

    private String text;

    public TextElement(final String name) {
        super(name);
    }

    public TextElement(final String name, final String text) {
        super(name);
        // delegate to setter to handle null-case
        setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
    }

    public boolean insertNewLine() {
        return true;
    }
}
