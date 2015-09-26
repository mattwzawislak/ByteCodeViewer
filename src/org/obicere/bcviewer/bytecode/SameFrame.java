package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SameFrame extends StackMapFrame {

    public SameFrame(final int frameType) {
        super(frameType);
    }

    @Override
    public int getOffsetDelta() {
        return getFrameType();
    }
}
