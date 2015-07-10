package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SameLocals1StackItemFrameExtended extends StackMapFrame {

    private final int                  offset;
    private final VerificationTypeInfo stack;

    public SameLocals1StackItemFrameExtended(final int frameType, final int offset, final VerificationTypeInfo stack) {
        super(frameType);

        if (stack == null) {
            throw new IllegalArgumentException("invalid verification type stack provided.");
        }

        this.offset = offset;
        this.stack = stack;
    }

    public int getOffset() {
        return offset;
    }

    public VerificationTypeInfo getStack(){
        return stack;
    }

}
