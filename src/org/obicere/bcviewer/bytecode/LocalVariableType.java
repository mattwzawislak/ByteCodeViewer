package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 * @author Obicere
 */
public class LocalVariableType extends BytecodeElement {

    private final int startPC;
    private final int length;
    private final int nameIndex;
    private final int signatureIndex;
    private final int index;

    public LocalVariableType(final int startPC, final int length, final int nameIndex, final int signatureIndex, final int index) {
        this.startPC = startPC;
        this.length = length;
        this.nameIndex = nameIndex;
        this.signatureIndex = signatureIndex;
        this.index = index;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getIntervalLength() {
        return length;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getSignatureIndex() {
        return signatureIndex;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final FieldSignature signature = SignatureAttribute.parseField(constantPool.getAsString(signatureIndex));

        signature.model(builder);
        builder.add(constantPool.getAsString(nameIndex));
    }
}
