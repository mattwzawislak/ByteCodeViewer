package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.SourceFileAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class SourceFileAttributeModeler implements Modeler<SourceFileAttribute> {
    @Override
    public void model(final SourceFileAttribute element, final DocumentBuilder builder) {
        final int sourceFileIndex = element.getSourceFileIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final String source = constantPool.getAsString(sourceFileIndex);

        builder.add("Source File: ");
        builder.add(source);
    }
}
