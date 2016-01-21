package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.TopVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class TopVariableInfoModeler implements Modeler<TopVariableInfo> {
    @Override
    public void model(final TopVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("top");
    }
}
