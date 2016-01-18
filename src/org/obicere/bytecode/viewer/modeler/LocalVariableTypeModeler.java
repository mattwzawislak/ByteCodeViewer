package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.LocalVariableType;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LocalVariableTypeModeler implements Modeler<LocalVariableType> {

    @Override
    public void model(final LocalVariableType element, final DocumentBuilder builder) {
        final int descriptorIndex = element.getSignatureIndex();
        final int nameIndex = element.getNameIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final FieldSignature signature = SignatureAttribute.parseField(constantPool.getAsString(descriptorIndex));

        builder.model(signature);
        builder.add(" ");
        builder.add(constantPool.getAsString(nameIndex));
    }
}
