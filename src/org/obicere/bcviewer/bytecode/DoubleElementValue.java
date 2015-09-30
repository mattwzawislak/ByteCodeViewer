package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class DoubleElementValue extends ElementValue {

    private static final int TAG = 'D';

    private final int constantValueIndex;

    public DoubleElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantDouble constant = (ConstantDouble) builder.getConstantPool().get(constantValueIndex);
        builder.add(constant.getBytes());
    }
}
