package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantFieldRef;
import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 */
public class ConstantFieldRefModeler implements Modeler<ConstantFieldRef> {
    @Override
    public void model(final ConstantFieldRef element, final DocumentBuilder builder) {

        final ConstantPool constantPool = builder.getConstantPool();
        builder.newLine();
        builder.tab();

        final int nameAndTypeIndex = element.getNameAndTypeIndex();
        final int classIndex = element.getClassIndex();

        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(nameAndTypeIndex);
        final FieldSignature signature = SignatureAttribute.parseField(constantPool.getAsString(nameAndType.getDescriptorIndex()));

        builder.model(signature);
        builder.add(" ");

        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (importMode) {
            builder.add(ByteCodeUtils.getClassName(constantPool.getAsString(classIndex)));
        } else {
            builder.add(ByteCodeUtils.getQualifiedName(constantPool.getAsString(classIndex)));
        }
        builder.add("#");
        builder.add(constantPool.getAsString(nameAndType.getNameIndex()));
    }
}
