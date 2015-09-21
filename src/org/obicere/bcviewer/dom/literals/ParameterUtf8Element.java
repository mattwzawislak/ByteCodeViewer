package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class ParameterUtf8Element extends TextElement {

    public ParameterUtf8Element(final String name, final String text, final DocumentBuilder builder) {
        super(name, text);

        setLeftPad(builder.getTabSize());

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_UTF8));
    }
}
