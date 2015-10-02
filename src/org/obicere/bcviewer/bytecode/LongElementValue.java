package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class LongElementValue extends ElementValue {

    private static final int TAG = 'J';

    private final int constantValueIndex;

    public LongElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        final ConstantLong constant = (ConstantLong) builder.getConstantPool().get(constantValueIndex);
        builder.add(constant.getBytes());
    }
}
