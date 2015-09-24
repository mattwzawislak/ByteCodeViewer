package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class AnnotationElement extends TextElement {

    public AnnotationElement(final String name, final String text, final DocumentBuilder builder) {
        super(name, text);
        setText(text);
        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_ANNOTATION));
    }

    @Override
    public void setText(final String text) {
        // make sure we add the @
        if (text.startsWith("@")) {
            super.setText(text);
        } else {
            super.setText("@" + text);
        }
    }
}
