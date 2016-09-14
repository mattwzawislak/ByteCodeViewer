package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.frame.AppendFrame;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class AppendFrameModeler extends StackMapFrameModeler<AppendFrame> {

    @Override
    protected void modelValue(final AppendFrame frame, final DocumentBuilder builder) {
        builder.add(" {");
        builder.indent();
        builder.newLine();

        builder.add("Locals:");
        modelInfo(builder, frame.getLocals());

        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
