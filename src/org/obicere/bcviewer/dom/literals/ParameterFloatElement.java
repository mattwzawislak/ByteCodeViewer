package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class ParameterFloatElement extends TextElement {

    public ParameterFloatElement(final String name, final float value, final DocumentBuilder builder) {
        super(name, String.valueOf(value));

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabSize());

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_NUMBER));
    }
}
