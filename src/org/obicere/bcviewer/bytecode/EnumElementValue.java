package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

/**
 * @author Obicere
 */
public class EnumElementValue extends ElementValue {

    private static final int TAG = 'e';

    private final int typeNameIndex;
    private final int constNameIndex;

    public EnumElementValue(final int typeNameIndex, final int constNameIndex) {
        super(TAG);
        this.typeNameIndex = typeNameIndex;
        this.constNameIndex = constNameIndex;
    }

    public int getTypeNameIndex() {
        return typeNameIndex;
    }

    public int getConstNameIndex() {
        return constNameIndex;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String type = BytecodeUtils.getQualifiedName(constantPool.getAsString(typeNameIndex));
        parent.add(new PlainElement("type", type.substring(1, type.length() - 1), builder));
        parent.add(new PlainElement("dot", ".", builder));
        parent.add(new PlainElement("name", constantPool.getAsString(getConstNameIndex()), builder));
    }
}
