package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.UninitializedThisVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class UninitializedThisVariableInfoModeler implements Modeler<UninitializedThisVariableInfo> {
    @Override
    public void model(final UninitializedThisVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("uninitialized_this");
    }
}
