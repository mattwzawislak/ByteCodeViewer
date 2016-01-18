package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.IntegerVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class IntegerVariableInfoModeler implements Modeler<IntegerVariableInfo> {
    @Override
    public void model(final IntegerVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("int");
    }
}
