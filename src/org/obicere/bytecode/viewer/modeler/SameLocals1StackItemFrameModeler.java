package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.SameLocals1StackItemFrame;
import org.obicere.bytecode.core.objects.VerificationTypeInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class SameLocals1StackItemFrameModeler extends StackMapFrameModeler<SameLocals1StackItemFrame> {
    @Override
    protected void modelValue(final SameLocals1StackItemFrame frame, final DocumentBuilder builder) {
        final VerificationTypeInfo stack = frame.getStack();

        builder.add(" {");
        builder.indent();
        builder.newLine();

        builder.add("Stack:");
        modelInfo(builder, new VerificationTypeInfo[]{stack});

        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
