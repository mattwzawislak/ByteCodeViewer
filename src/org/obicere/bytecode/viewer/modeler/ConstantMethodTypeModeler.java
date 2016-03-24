package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantMethodType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantMethodTypeModeler implements Modeler<ConstantMethodType> {
    @Override
    public void model(final ConstantMethodType element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final int descriptorIndex = element.getDescriptorIndex();
        final String descriptor = constantPool.getAsString(descriptorIndex);
        final MethodSignature signature = MethodSignature.parse(descriptor);

        builder.model(signature.getResult());
        builder.pad(1);
        builder.model(signature.getParameters());
    }
}
