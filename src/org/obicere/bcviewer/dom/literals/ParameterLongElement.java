package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.AttributesResourcePool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class ParameterLongElement extends LongElement {

    public ParameterLongElement(final String name, final long value, final DocumentBuilder builder) {
        super(name, value, builder);
        setLeftPad(builder.getTabSize());
    }
}
