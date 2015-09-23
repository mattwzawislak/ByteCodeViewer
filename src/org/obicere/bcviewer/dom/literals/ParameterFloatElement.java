package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class ParameterFloatElement extends FloatElement {

    public ParameterFloatElement(final String name, final float value, final DocumentBuilder builder) {
        super(name, value, builder);
        setLeftPad(builder.getTabSize());
    }
}
