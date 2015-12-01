package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.ByteCodeUtils;

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
    public void model(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String type = ByteCodeUtils.getQualifiedName(constantPool.getAsString(typeNameIndex));

        builder.add(ByteCodeUtils.getQualifiedName(type));
        builder.add(".");
        builder.add(constantPool.getAsString(getConstNameIndex()));
    }
}
