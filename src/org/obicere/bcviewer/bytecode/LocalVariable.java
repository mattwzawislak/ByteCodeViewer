package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVariable {

    private final int startPC;
    private final int length;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int index;

    public LocalVariable(final int startPC, final int length, final int nameIndex, final int descriptorIndex, final int index){
        this.startPC = startPC;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.index = index;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public int getLength() {
        return length;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getIndex() {
        return index;
    }
}
