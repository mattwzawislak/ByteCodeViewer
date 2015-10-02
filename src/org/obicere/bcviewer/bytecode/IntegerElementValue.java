package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class IntegerElementValue extends ElementValue {

    private static final int TAG = 'I';

    private final int constantValueIndex;

    public IntegerElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        final ConstantInteger constant = (ConstantInteger) builder.getConstantPool().get(constantValueIndex);
        builder.add(constant.getBytes());
    }
}
