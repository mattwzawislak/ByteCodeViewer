package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.KeywordElement;

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
    public void model(final DocumentBuilder builder, final Element parent) {
        final String value = builder.getConstantPool().getAsString(constantValueIndex);
        parent.add(new KeywordElement("value", value.equals("0") ? "false" : "true", builder));
    }
}
