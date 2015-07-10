package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SourceFileAttribute extends Attribute {

    private final int sourceFileIndex;

    public SourceFileAttribute(final int attributeNameIndex, final int attributeLength, final int sourceFileIndex) {
        super(attributeNameIndex, attributeLength);
        this.sourceFileIndex = sourceFileIndex;
    }

    public int getSourceFileIndex() {
        return sourceFileIndex;
    }
}
