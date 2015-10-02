package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class AppendFrame extends StackMapFrame {

    private static final String NAME = "AppendFrame";

    private final int                    offset;
    private final VerificationTypeInfo[] locals;

    public AppendFrame(final int frameType, final int offset, final VerificationTypeInfo[] locals) {
        super(frameType);

        if (locals.length != frameType - 251) {
            throw new IllegalArgumentException("invalid locals count for frame: " + locals.length);
        }

        this.offset = offset;
        this.locals = locals;
    }

    public int getOffset() {
        return offset;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
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
    public void modelValue(final DocumentBuilder builder) {
        builder.newLine();
        builder.add("Locals:");
        modelInfo(builder, locals);
    }
}
