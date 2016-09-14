package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.block.ImplicitCodeBlock;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class ImplicitCodeBlockModeler extends CodeBlockModeler<ImplicitCodeBlock> {
    @Override
    public void model(final ImplicitCodeBlock element, final DocumentBuilder builder) {
        modelInstructions(element.getInstructions(), builder);
    }
}
