package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class IntegerElement extends TextElement {

    public IntegerElement(final String name, final int value, final DocumentBuilder builder) {
        super(name, String.valueOf(value));

        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_NUMBER));
    }
}
