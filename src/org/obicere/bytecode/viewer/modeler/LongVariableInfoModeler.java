package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.frame.verification.LongVariableInfo;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LongVariableInfoModeler implements Modeler<LongVariableInfo> {
    @Override
    public void model(final LongVariableInfo element, final DocumentBuilder builder) {
        builder.addKeyword("long");
    }
}
