package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.core.objects.signature.Parameters;
import org.obicere.bytecode.core.objects.signature.Result;
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
        final MethodSignature methodSignature = MethodSignature.parse(signature);
        if (methodSignature != null) {
            final Result result = methodSignature.getResult();
            final Parameters parameters = methodSignature.getParameters();

            builder.model(result);
            builder.add("#");
            builder.add(name);
            builder.model(parameters);
        } else {
            final FieldSignature fieldSignature = FieldSignature.parse(signature);
            if (fieldSignature != null) {
                builder.model(fieldSignature);
                builder.add("#");
                builder.add(name);
            }
        }
    }
}
