package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVariableType {

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

    public int getLength() {
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
}
