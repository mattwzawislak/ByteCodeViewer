package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String type = BytecodeUtils.getQualifiedName(constantPool.getAsString(typeNameIndex));

        builder.addPlain(parent, BytecodeUtils.getQualifiedName(type.substring(1, type.length() - 1)));
        builder.addPlain(parent, ".");
        builder.addPlain(parent, constantPool.getAsString(getConstNameIndex()));
    }
}
