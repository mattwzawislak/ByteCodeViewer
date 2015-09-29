package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final String name = getName();
        builder.addPlain(parent, name + " {");

        builder.indent();
        builder.newLine(parent);

        builder.addPlain(parent, "Offset: ");
        builder.add(parent, getOffsetDelta());

        modelValue(builder, parent);

        builder.unindent();
        builder.newLine(parent);
        builder.addPlain(parent, "}");
    }

    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {

    }

    public void modelInfo(final BytecodeDocumentBuilder builder, final Element parent, final VerificationTypeInfo[] info) {

        if (info.length > 0) {
            builder.indent();
            builder.newLine(parent);
            builder.addPlain(parent, "[");
            boolean first = true;
            int count = 0;
            for (final VerificationTypeInfo local : info) {
                if (!first) {
                    builder.comma(parent);
                }
                if (count == 4) {
                    count = 0;
                    builder.newLine(parent);
                }
                local.model(builder, parent);
                first = false;
                count++;
            }
            builder.addPlain(parent, "]");
            builder.unindent();
        } else {
            builder.addPlain(parent, " []");
        }
    }
}
