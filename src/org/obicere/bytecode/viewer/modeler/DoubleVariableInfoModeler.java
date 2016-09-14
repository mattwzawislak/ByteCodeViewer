package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.frame.verification.DoubleVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class DoubleVariableInfoModeler implements Modeler<DoubleVariableInfo> {
    @Override
    public void model(final DoubleVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("double");
    }
}
