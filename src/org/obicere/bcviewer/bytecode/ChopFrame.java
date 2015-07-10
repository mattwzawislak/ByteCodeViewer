package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ChopFrame extends StackMapFrame {

    private final int offset;

    public ChopFrame(final int frameType, final int offset) {
        super(frameType);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

}
