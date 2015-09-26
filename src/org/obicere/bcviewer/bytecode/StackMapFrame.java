package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class StackMapFrame extends BytecodeElement {

    private final int frameType;

    public StackMapFrame(final int frameType) {
        this.frameType = frameType;
    }

    public int getFrameType() {
        return frameType;
    }

    public abstract int getOffsetDelta();
}
