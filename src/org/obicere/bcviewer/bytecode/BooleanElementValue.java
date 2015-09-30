package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class BooleanElementValue extends ElementValue {

    private static final int TAG = 'Z';

    private final int constantValueIndex;

    public BooleanElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final String value = builder.getConstantPool().getAsString(constantValueIndex);
        builder.add(value.equals("true"));
    }
}
