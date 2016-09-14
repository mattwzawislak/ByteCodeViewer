package org.obicere.bytecode.viewer.modeler.label;

import org.obicere.bytecode.core.objects.code.block.CodeBlock;
import org.obicere.bytecode.core.objects.code.block.label.CodeBlockLabel;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public class CodeBlockLabelModeler implements Modeler<CodeBlockLabel> {
    @Override
    public void model(final CodeBlockLabel element, final DocumentBuilder builder) {
        final CodeBlock block = element.getCodeBlock();
        final int offset = element.getOffset();

        if (block != null) {
            builder.add(block.getName());
            if (offset != 0) {
                builder.add(" + ");
                builder.add(offset);
            }
        } else {
            builder.add(offset);
        }
    }
}
