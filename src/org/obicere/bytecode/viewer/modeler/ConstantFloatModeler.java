package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantFloat;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantFloatModeler implements Modeler<ConstantFloat> {
    @Override
    public void model(final ConstantFloat element, final DocumentBuilder builder) {
        builder.add(element.getBytes());
    }
}
