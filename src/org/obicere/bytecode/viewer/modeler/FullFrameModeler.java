package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.FullFrame;
import org.obicere.bytecode.core.objects.VerificationTypeInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class FullFrameModeler extends StackMapFrameModeler<FullFrame> {
    @Override
    protected void modelValue(final FullFrame frame, final DocumentBuilder builder) {
        final VerificationTypeInfo[] locals = frame.getLocals();
        final VerificationTypeInfo[] stack = frame.getStack();

        builder.newLine();
        builder.add("Locals:");
        modelInfo(builder, locals);

        builder.newLine();
        builder.add("Stack:");
        modelInfo(builder, stack);
    }
}
