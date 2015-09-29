package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;

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
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        builder.newLine(parent);
        builder.tab(parent);

        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(nameAndTypeIndex);
        final FieldSignature signature = SignatureAttribute.parseField(constantPool.getAsString(nameAndType.getDescriptorIndex()));
        signature.model(builder, parent);
        builder.addPlain(parent, " ");

        builder.addPlain(parent, BytecodeUtils.getQualifiedName(constantPool.getAsString(getClassIndex())));
        builder.addPlain(parent, "#");
        builder.addPlain(parent, BytecodeUtils.getQualifiedName(constantPool.getAsString(nameAndType.getNameIndex())));

    }
}
