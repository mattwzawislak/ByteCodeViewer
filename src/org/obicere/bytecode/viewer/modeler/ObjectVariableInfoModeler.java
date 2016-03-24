package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.ObjectVariableInfo;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ObjectVariableInfoModeler implements Modeler<ObjectVariableInfo> {
    @Override
    public void model(final ObjectVariableInfo element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final int index = element.getIndex();
        final String signature = constantPool.getAsString(index);

        final FieldSignature classSignature = FieldSignature.parse(signature);

        builder.model(classSignature);
    }
}
