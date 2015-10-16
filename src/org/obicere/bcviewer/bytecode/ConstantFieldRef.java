package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;
import org.obicere.bcviewer.util.BytecodeUtils;

/**
 * @author Obicere
 */
public class ConstantFieldRef extends Constant {

    private static final String NAME = "FieldRef";

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantFieldRef(final int classIndex, final int nameAndTypeIndex) {
        super(ConstantReader.CONSTANT_FIELD_REF);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(classIndex) + ";" + constantPool.getAsString(nameAndTypeIndex);
    }

    @Override
    public void modelValue(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        builder.newLine();
        builder.tab();

        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(nameAndTypeIndex);
        final FieldSignature signature = SignatureAttribute.parseField(constantPool.getAsString(nameAndType.getDescriptorIndex()));
        signature.model(builder);
        builder.add(" ");

        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (importMode) {
            builder.add(BytecodeUtils.getClassName(constantPool.getAsString(getClassIndex())));
        } else {
            builder.add(BytecodeUtils.getQualifiedName(constantPool.getAsString(getClassIndex())));
        }
        builder.add("#");
        builder.add(constantPool.getAsString(nameAndType.getNameIndex()));

    }
}
