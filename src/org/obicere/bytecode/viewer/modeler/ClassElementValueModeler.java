package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.annotation.ClassElementValue;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ClassElementValueModeler implements Modeler<ClassElementValue> {
    @Override
    public void model(final ClassElementValue element, final DocumentBuilder builder) {
        final int classInfoIndex = element.getClassInfoIndex();
        final ConstantPool constantPool = builder.getConstantPool();
        final String type = ByteCodeUtils.getQualifiedName(constantPool.getAsString(classInfoIndex));

        builder.tab();
        builder.add(type);
        builder.add(".");
        builder.addKeyword("class");
    }
}
