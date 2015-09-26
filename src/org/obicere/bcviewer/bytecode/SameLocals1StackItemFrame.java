package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SameLocals1StackItemFrame extends StackMapFrame {

    private final VerificationTypeInfo stack;

    public SameLocals1StackItemFrame(final int frameType, final VerificationTypeInfo stack) {
        super(frameType);

        if(stack == null){
            throw new IllegalArgumentException("invalid verification type stack provided.");
        }

        this.stack = stack;
    }

    public VerificationTypeInfo getStack(){
        return stack;
    }

    @Override
    public int getOffsetDelta() {
        return getFrameType() - 64;
    }
}
