package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.StackMapFrame;
import org.obicere.bytecode.core.objects.VerificationTypeInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public abstract class StackMapFrameModeler<F extends StackMapFrame> implements Modeler<F> {

    @Override
    public void model(final F frame, final DocumentBuilder builder) {
        final String name = frame.getName();
        builder.add(name);

        //builder.add("Offset: ");
        //builder.add(frame.getOffsetDelta());

        modelValue(frame, builder);
    }

    protected void modelInfo(final DocumentBuilder builder, final VerificationTypeInfo[] info) {

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
                builder.model(local);
                first = false;
                count++;
            }
            builder.add("]");
            builder.unindent();
        } else {
            builder.add(" []");
        }
    }

    protected abstract void modelValue(final F frame, final DocumentBuilder builder);
}
