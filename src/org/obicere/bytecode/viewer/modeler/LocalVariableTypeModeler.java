package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.code.table.LocalVariableType;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LocalVariableTypeModeler implements Modeler<LocalVariableType> {

    @Override
    public void model(final LocalVariableType element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final Label start = element.getStart();
        final Label end = element.getEnd();

        final int index = element.getIndex();
        final int descriptorIndex = element.getSignatureIndex();
        final int nameIndex = element.getNameIndex();
        final String descriptor = constantPool.getAsString(descriptorIndex);
        final String name = constantPool.getAsString(nameIndex);

        final FieldSignature signature = FieldSignature.parse(descriptor);

        builder.model(start);
        builder.add(",");
        builder.tab();
        builder.model(end);
        builder.add(",");
        builder.tab();
        builder.add(index);
        builder.add(",");
        builder.tab();
        builder.model(signature);
        builder.add(" ");
        builder.add(name);
    }
}
