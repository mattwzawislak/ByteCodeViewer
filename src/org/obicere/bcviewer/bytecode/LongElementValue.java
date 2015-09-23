package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.LongElement;

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
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantLong constant = (ConstantLong) builder.getConstantPool().get(constantValueIndex);
        parent.add(new LongElement("value", constant.getBytes(), builder));
    }
}
