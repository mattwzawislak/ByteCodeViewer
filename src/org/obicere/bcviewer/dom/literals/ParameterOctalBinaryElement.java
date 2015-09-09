package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.OctalElement;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;

/**
 */
public class ParameterOctalBinaryElement extends OctalElement {

    public ParameterOctalBinaryElement(final String name, final int number, final DocumentBuilder builder) {
        super(name, number);

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabbedPaddingSize(getText().length(), builder.getTabSize()));

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_NUMBER));
    }

}
