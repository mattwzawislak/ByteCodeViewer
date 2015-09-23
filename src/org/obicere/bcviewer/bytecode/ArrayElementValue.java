package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.PlainElement;

/**
 * @author Obicere
 */
public class ArrayElementValue extends ElementValue {

    private static final int TAG = '[';

    private final ElementValue[] values;

    public ArrayElementValue(final ElementValue[] values) {
        super(TAG);
        this.values = values;
    }

    public ElementValue[] getValues() {
        return values;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new PlainElement("open", "{", builder));

        boolean first = true;

        for (final ElementValue value : values) {
            if (!first) {
                final PlainElement comma = new PlainElement("comma", ",", builder);
                comma.setRightPad(1);
                parent.add(comma);
            }
            value.model(builder, parent);
            first = false;
        }
        parent.add(new PlainElement("close", "}", builder));
    }
}
