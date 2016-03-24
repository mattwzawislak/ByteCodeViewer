package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.LocalVariableType;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LocalVariableTypeModeler implements Modeler<LocalVariableType> {

    @Override
    public void model(final LocalVariableType element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final int descriptorIndex = element.getSignatureIndex();
        final int nameIndex = element.getNameIndex();
        final String descriptor = constantPool.getAsString(descriptorIndex);
        final String name = constantPool.getAsString(nameIndex);

        final FieldSignature signature = FieldSignature.parse(descriptor);

        builder.model(signature);
        builder.add(" ");
        builder.add(name);
    }
}
