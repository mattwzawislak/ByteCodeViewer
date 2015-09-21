package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 */
public class ParameterPlainElement extends PlainElement {

    public ParameterPlainElement(final String name, final String text, final DocumentBuilder builder) {
        super(name, text, builder);
        setLeftPad(builder.getTabSize());
    }
}
