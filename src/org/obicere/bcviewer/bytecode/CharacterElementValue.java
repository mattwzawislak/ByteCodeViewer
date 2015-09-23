package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.IntegerElement;
import org.obicere.bcviewer.dom.literals.PlainElement;

/**
 * @author Obicere
 */
public class CharacterElementValue extends ElementValue {

    private static final int TAG = 'C';

    private final int constantValueIndex;

    public CharacterElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantInteger constant = (ConstantInteger) builder.getConstantPool().get(constantValueIndex);
        parent.add(new PlainElement("value", String.valueOf((char) constant.getBytes()), builder));
    }
}