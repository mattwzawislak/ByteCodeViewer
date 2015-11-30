package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVariableType extends LocalVariable {

    public LocalVariableType(final int startPC, final int length, final int nameIndex, final int signatureIndex, final int index) {
        super(startPC, length, nameIndex, signatureIndex, index);
    }

    public int getSignatureIndex() {
        return getDescriptorIndex();
    }
}
