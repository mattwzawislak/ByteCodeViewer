package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.FrameCodeBlock;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class FrameCodeBlockModeler implements Modeler<FrameCodeBlock> {
    @Override
    public void model(final FrameCodeBlock element, final DocumentBuilder builder) {

        builder.newLine();
        builder.model(element.getFrame());
    }
}
