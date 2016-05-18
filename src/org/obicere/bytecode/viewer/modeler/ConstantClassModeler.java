package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantClass;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ConstantClassModeler implements Modeler<ConstantClass> {
    @Override
    public void model(final ConstantClass element, final DocumentBuilder builder) {

        final ConstantPool constantPool = builder.getConstantPool();
        final int nameIndex = element.getNameIndex();
        final String name = constantPool.getAsString(nameIndex);

        final FieldSignature signature = FieldSignature.parse(name);

        if (signature != null) {
            builder.model(signature);
        } else {

            final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
            if (importMode) {
                builder.add(ByteCodeUtils.getClassName(name));
            } else {
                builder.add(ByteCodeUtils.getQualifiedName(name));
            }
        }

        builder.add(".");
        builder.addKeyword("class");
    }
}
