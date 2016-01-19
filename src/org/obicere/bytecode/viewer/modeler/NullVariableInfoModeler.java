package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.NullVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class NullVariableInfoModeler implements Modeler<NullVariableInfo> {
    @Override
    public void model(final NullVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("null");
    }
}
