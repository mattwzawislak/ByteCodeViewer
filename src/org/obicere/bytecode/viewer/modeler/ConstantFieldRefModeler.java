package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantFieldRef;
import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ConstantFieldRefModeler implements Modeler<ConstantFieldRef> {
    @Override
    public void model(final ConstantFieldRef element, final DocumentBuilder builder) {

        final ConstantPool constantPool = builder.getConstantPool();

        final int nameAndTypeIndex = element.getNameAndTypeIndex();
        final int classIndex = element.getClassIndex();
        final String className = constantPool.getAsString(classIndex);

        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(nameAndTypeIndex);
        final String fieldSignature = constantPool.getAsString(nameAndType.getDescriptorIndex());
        final FieldSignature signature = FieldSignature.parse(fieldSignature);

        builder.model(signature);
        builder.pad(1);

        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (importMode) {
            builder.add(ByteCodeUtils.getClassName(className));
        } else {
            builder.add(ByteCodeUtils.getQualifiedName(className));
        }
        builder.add("#");
        builder.add(constantPool.getAsString(nameAndType.getNameIndex()));
    }
}
