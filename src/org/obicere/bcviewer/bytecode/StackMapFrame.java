package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class StackMapFrame {

    private final int frameType;

    public StackMapFrame(final int frameType) {
        this.frameType = frameType;
    }

    public int getFrameType() {
        return frameType;
    }
}
