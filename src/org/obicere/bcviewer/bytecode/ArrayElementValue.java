package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {

        builder.addPlain("{");
        boolean first = true;
        for (final ElementValue value : values) {
            if (!first) {
                builder.comma();
            }
            value.model(builder, parent);
            first = false;
        }
        builder.addPlain("}");
    }
}
