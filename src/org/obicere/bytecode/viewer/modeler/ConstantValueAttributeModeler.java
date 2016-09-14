package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.constant.Constant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.attribute.ConstantValueAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantValueAttributeModeler implements Modeler<ConstantValueAttribute> {
    @Override
    public void model(final ConstantValueAttribute element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(constantValueIndex);

        builder.add(" = ");
        builder.model(constant);
    }
}
