package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantNameAndTypeModeler implements Modeler<ConstantNameAndType> {
    @Override
    public void model(final ConstantNameAndType element, final DocumentBuilder builder) {
        final int nameIndex = element.getNameIndex();
        final int descriptorIndex = element.getDescriptorIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final String name = constantPool.getAsString(nameIndex);

        final String signature = constantPool.getAsString(descriptorIndex);
        final MethodSignature methodSignature = SignatureAttribute.parseMethod(signature);
        if (methodSignature != null) {
            // TODO work out signature details
            //methodSignature.modelReturnType(builder);
            builder.add(" ");
            builder.add(name);
            // TODO work out signature details
            //methodSignature.modelParameters(builder);
        } else {
            final FieldSignature fieldSignature = SignatureAttribute.parseField(signature);
            if (fieldSignature != null) {
                builder.model(fieldSignature);
                builder.add(name);
            }
        }
    }
}
