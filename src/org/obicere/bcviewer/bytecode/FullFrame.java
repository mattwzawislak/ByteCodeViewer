package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class FullFrame extends StackMapFrame {

    private static final String NAME = "FullFrame";

    private final int                    offset;
    private final VerificationTypeInfo[] locals;
    private final VerificationTypeInfo[] stack;

    public FullFrame(final int frameType, final int offset, final VerificationTypeInfo[] locals, final VerificationTypeInfo[] stack) {
        super(frameType);

        if (locals == null) {
            throw new NullPointerException("locals not defined.");
        }

        if (stack == null) {
            throw new NullPointerException("stack not defined.");
        }

        this.offset = offset;
        this.locals = locals;
        this.stack = stack;
    }

    @Override
    public int getOffsetDelta() {
        return offset;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
    }

    public VerificationTypeInfo[] getStack() {
        return stack;
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent){

        builder.newLine();
        builder.addPlain("Locals:");
        modelInfo(builder, parent, locals);

        builder.newLine();
        builder.addPlain("Stack:");
        modelInfo(builder, parent, stack);
    }
}
