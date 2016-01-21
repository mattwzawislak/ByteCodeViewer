package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.UninitializedVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class UninitializedVariableInfoModeler implements Modeler<UninitializedVariableInfo> {
    @Override
    public void model(final UninitializedVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("uninitialized_var");
    }
}
