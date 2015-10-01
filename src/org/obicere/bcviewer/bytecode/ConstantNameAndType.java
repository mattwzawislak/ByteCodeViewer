package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantNameAndType extends Constant {

    private static final String NAME = "NameAndType";

    private final int nameIndex;

    private final int descriptorIndex;

    public ConstantNameAndType(final int nameIndex, final int descriptorIndex) {
        super(ConstantReader.CONSTANT_NAME_AND_TYPE);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(nameIndex) + ";" + constantPool.getAsString(descriptorIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String name = constantPool.getAsString(nameIndex);

        final String signature = constantPool.getAsString(descriptorIndex);
        final MethodSignature methodSignature = SignatureAttribute.parseMethod(signature);
        if (methodSignature != null) {
            methodSignature.modelReturnType(builder);
            builder.add(" " + name);
            methodSignature.modelParameters(builder);
        } else {
            final FieldSignature fieldSignature = SignatureAttribute.parseField(signature);
            if (fieldSignature != null) {
                fieldSignature.model(builder);
                builder.add(name);
            }
        }
    }
}
