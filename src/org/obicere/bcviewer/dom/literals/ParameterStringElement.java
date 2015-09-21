package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.StringElement;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;

/**
 */
public class ParameterStringElement extends StringElement {

    public ParameterStringElement(final String name, final String text, final DocumentBuilder builder) {
        super(name, text);

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabSize());

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_STRING));
    }
}
