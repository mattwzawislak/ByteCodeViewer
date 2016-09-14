package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.annotation.EnumElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class EnumElementValueModeler implements Modeler<EnumElementValue> {
    @Override
    public void model(final EnumElementValue element, final DocumentBuilder builder) {
        final int typeNameIndex = element.getTypeNameIndex();
        final int constNameIndex = element.getConstNameIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final String type = constantPool.getAsString(typeNameIndex);

        builder.add(ByteCodeUtils.getQualifiedName(type));
        builder.add(".");
        builder.add(constantPool.getAsString(constNameIndex));
    }
}
