package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

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

    public abstract String getName();

    @Override
    public void model(final DocumentBuilder builder) {
        final String name = getName();
        builder.add(name + " {");

        builder.indent();
        builder.newLine();

        builder.add("Offset: ");
        builder.add(getOffsetDelta());

        modelValue(builder);

        builder.unindent();
        builder.newLine();
        builder.add("}");
    }

    public void modelValue(final DocumentBuilder builder) {

    }

    public void modelInfo(final DocumentBuilder builder, final VerificationTypeInfo[] info) {

        if (info.length > 0) {
            builder.indent();
            builder.newLine();
            builder.add("[");
            boolean first = true;
            int count = 0;
            for (final VerificationTypeInfo local : info) {
                if (!first) {
                    builder.comma();
                }
                if (count == 4) {
                    count = 0;
                    builder.newLine();
                }
                local.model(builder);
                first = false;
                count++;
            }
            builder.add("]");
            builder.unindent();
        } else {
            builder.add(" []");
        }
    }
}
