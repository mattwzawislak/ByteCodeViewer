package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.block.FrameCodeBlock;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class FrameCodeBlockModeler extends CodeBlockModeler<FrameCodeBlock> {
    @Override
    public void model(final FrameCodeBlock element, final DocumentBuilder builder) {

        builder.add(element.getName());
        builder.add(" {");
        builder.indent();
        builder.newLine();
        builder.model(element.getFrame());
        builder.newLine();

        modelInstructions(element.getInstructions(), builder);

        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
