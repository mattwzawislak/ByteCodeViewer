package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class StringElement extends TextElement {

    public StringElement(final String name, final String text, final DocumentBuilder builder) {
        super(name, text);
        setText(text);

        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_STRING));
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
