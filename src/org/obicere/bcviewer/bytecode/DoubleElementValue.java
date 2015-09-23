package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.DoubleElement;

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
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantDouble constant = (ConstantDouble) builder.getConstantPool().get(constantValueIndex);
        parent.add(new DoubleElement("value", constant.getBytes(), builder));
    }
}
