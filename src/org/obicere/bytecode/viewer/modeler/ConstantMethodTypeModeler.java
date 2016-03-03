package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantMethodType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantMethodTypeModeler implements Modeler<ConstantMethodType> {
    @Override
    public void model(final ConstantMethodType element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final int descriptorIndex = element.getDescriptorIndex();
        final Constant constant = constantPool.get(descriptorIndex);
        builder.model(constant);
        //builder.add(constant.toString(constantPool));
    }
}
