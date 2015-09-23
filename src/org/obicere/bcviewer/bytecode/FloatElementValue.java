package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.FloatElement;

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
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantFloat constant = (ConstantFloat) builder.getConstantPool().get(constantValueIndex);
        parent.add(new FloatElement("value", constant.getBytes(), builder));
    }
}
