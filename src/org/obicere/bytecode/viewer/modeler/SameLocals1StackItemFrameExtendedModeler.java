package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.SameLocals1StackItemFrameExtended;
import org.obicere.bytecode.core.objects.VerificationTypeInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class SameLocals1StackItemFrameExtendedModeler extends StackMapFrameModeler<SameLocals1StackItemFrameExtended> {
    @Override
    protected void modelValue(final SameLocals1StackItemFrameExtended frame, final DocumentBuilder builder) {
        final VerificationTypeInfo stack = frame.getStack();

        builder.newLine();
        builder.add("Stack:");
        modelInfo(builder, new VerificationTypeInfo[]{stack});
    }
}
