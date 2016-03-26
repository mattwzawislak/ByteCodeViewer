package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.LineCodeBlock;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class LineCodeBlockModeler extends CodeBlockModeler<LineCodeBlock> {
    @Override
    public void model(final LineCodeBlock element, final DocumentBuilder builder) {

        builder.add(element.getName());
        builder.add(" {");
        builder.indent();
        builder.newLine();

        modelInstructions(element.getInstructions(), builder);

        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
