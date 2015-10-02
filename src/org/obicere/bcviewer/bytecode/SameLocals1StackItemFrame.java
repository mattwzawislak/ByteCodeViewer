package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class SameLocals1StackItemFrame extends StackMapFrame {

    private static final String NAME = "SameLocalsFrame";

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

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void modelValue(final DocumentBuilder builder) {
        builder.newLine();
        builder.add("Stack:");
        modelInfo(builder, new VerificationTypeInfo[]{stack});
    }
}
