package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ChopFrame extends StackMapFrame {

    private static final String NAME = "ChopFrame";

    private final int offset;

    public ChopFrame(final int frameType, final int offset) {
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
