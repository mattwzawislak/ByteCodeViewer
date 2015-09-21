package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class DoubleElement extends TextElement {

    public DoubleElement(final String name, final double value, final DocumentBuilder builder) {
        super(name, String.valueOf(value));

        setAttributes(builder.getAttributesPool().get(AttributesResourcePool.ATTRIBUTES_NUMBER));
    }
}
