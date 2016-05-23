package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LocalVariableModeler implements Modeler<LocalVariable> {
    @Override
    public void model(final LocalVariable element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final Label start = element.getStart();
        final Label end = element.getEnd();

        final int index = element.getIndex();
        final int descriptorIndex = element.getDescriptorIndex();
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
