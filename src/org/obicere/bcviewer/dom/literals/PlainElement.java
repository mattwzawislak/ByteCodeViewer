package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class PlainElement extends TextElement {

    public PlainElement(final String name, final String text, final DocumentBuilder builder) {
        super(name, text);

        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_PLAIN));
    }
}
