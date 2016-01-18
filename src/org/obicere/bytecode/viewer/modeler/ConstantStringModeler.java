package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.ConstantString;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantStringModeler implements Modeler<ConstantString> {
    @Override
    public void model(final ConstantString element, final DocumentBuilder builder) {
        final int stringIndex = element.getStringIndex();
        final ConstantPool constantPool = builder.getConstantPool();
        builder.addString(constantPool.getAsString(stringIndex));
    }
}
