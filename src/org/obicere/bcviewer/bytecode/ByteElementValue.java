package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class ByteElementValue extends ElementValue {

    private static final int TAG = 'B';

    private final int constantValueIndex;

    public ByteElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        final ConstantInteger constant = (ConstantInteger) builder.getConstantPool().get(constantValueIndex);
        builder.add((byte) constant.getBytes());
    }
}
