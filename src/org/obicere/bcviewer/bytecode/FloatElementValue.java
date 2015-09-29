package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class FloatElementValue extends ElementValue {

    private static final int TAG = 'F';

    private final int constantValueIndex;

    public FloatElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantFloat constant = (ConstantFloat) builder.getConstantPool().get(constantValueIndex);
        builder.add(parent, constant.getBytes());
    }
}
