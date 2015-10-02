package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

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
    public void model(final DocumentBuilder builder) {

        builder.add("{");
        boolean first = true;
        for (final ElementValue value : values) {
            if (!first) {
                builder.comma();
            }
            value.model(builder);
            first = false;
        }
        builder.add("}");
    }
}
