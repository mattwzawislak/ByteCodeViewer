package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class SameLocals1StackItemFrameExtended extends StackMapFrame {

    private static final String NAME = "SameLocalsExtendedFrame";

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

    public VerificationTypeInfo getStack(){
        return stack;
    }

    @Override
    public int getOffsetDelta() {
        return offset;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.newLine();
        builder.addPlain("Stack:");
        modelInfo(builder, parent, new VerificationTypeInfo[]{stack});
    }
}
