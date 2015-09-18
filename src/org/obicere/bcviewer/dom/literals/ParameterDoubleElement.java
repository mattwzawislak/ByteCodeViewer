package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class ParameterDoubleElement extends TextElement {

    public ParameterDoubleElement(final String name, final double value, final DocumentBuilder builder) {
        super(name, String.valueOf(value));

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabbedPaddingSize(getText().length(), builder.getTabSize()));

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_NUMBER));
    }
}
