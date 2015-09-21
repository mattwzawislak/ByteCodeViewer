package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class ParameterIntegerElement extends IntegerElement {

    public ParameterIntegerElement(final String name, final int value, final DocumentBuilder builder) {
        super(name, value, builder);
        setLeftPad(builder.getTabSize());
    }

}
