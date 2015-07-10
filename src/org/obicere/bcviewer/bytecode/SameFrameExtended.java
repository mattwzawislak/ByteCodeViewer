package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SameFrameExtended extends StackMapFrame {

    private final int offset;

    public SameFrameExtended(final int frameType, final int offset) {
        super(frameType);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

}
