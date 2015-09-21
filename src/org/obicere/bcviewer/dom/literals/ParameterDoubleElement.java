package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class ParameterDoubleElement extends DoubleElement {

    public ParameterDoubleElement(final String name, final double value, final DocumentBuilder builder) {
        super(name, value, builder);
        setLeftPad(builder.getTabSize());
    }
}
