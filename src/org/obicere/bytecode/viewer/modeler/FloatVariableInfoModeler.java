package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.FloatVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class FloatVariableInfoModeler implements Modeler<FloatVariableInfo> {
    @Override
    public void model(final FloatVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("float");
    }
}
