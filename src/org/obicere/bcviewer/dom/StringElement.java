package org.obicere.bcviewer.dom;

import java.awt.Font;

/**
 */
public class StringElement extends TextElement {
    public StringElement(final String name) {
        this(name, null);
    }

    public StringElement(final String name, final String text) {
        super(name, text);
    }

    @Override
    public void setText(final String text) {
        super.setText(applyChanges(text));
    }

    private String applyChanges(String text) {
        if (text == null) {
            return "null";
        }
        text = text.replace("\\", "\\\\");
        text = text.replace("\"", "\\\"");
        text = text.replace("\b", "\\b");
        text = text.replace("\f", "\\f");
        text = text.replace("\n", "\\n");
        text = text.replace("\r", "\\r");
        text = text.replace("\t", "\\t");

        text = "\"" + text + "\"";
        return text;
    }
}
