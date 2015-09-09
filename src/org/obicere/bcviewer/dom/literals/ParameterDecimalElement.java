package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DecimalElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;

/**
 */
public class ParameterDecimalElement extends DecimalElement {

    public ParameterDecimalElement(final String name, final int parameter, final DocumentBuilder builder) {
        super(name, parameter);

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabbedPaddingSize(getText().length(), builder.getTabSize()));

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_NUMBER));
    }

}
