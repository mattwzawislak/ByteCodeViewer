package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LocalVariableModeler implements Modeler<LocalVariable> {
    @Override
    public void model(final LocalVariable element, final DocumentBuilder builder) {
        final int descriptorIndex = element.getDescriptorIndex();
        final int nameIndex = element.getNameIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final FieldSignature signature = SignatureAttribute.parseField(constantPool.getAsString(descriptorIndex));

        builder.model(signature);
        builder.add(" ");
        builder.add(constantPool.getAsString(nameIndex));
    }
}
