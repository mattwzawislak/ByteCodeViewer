package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SameFrameExtended extends StackMapFrame {

    private static final String NAME = "SameFrameExtended";

    private final int offset;

    public SameFrameExtended(final int frameType, final int offset) {
        super(frameType);
        this.offset = offset;
    }

    @Override
    public int getOffsetDelta() {
        return offset;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
