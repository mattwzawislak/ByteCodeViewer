package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SameFrame extends StackMapFrame {

    private static final String NAME = "SameFrame";

    public SameFrame(final int frameType) {
        super(frameType);
    }

    @Override
    public int getOffsetDelta() {
        return getFrameType();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
