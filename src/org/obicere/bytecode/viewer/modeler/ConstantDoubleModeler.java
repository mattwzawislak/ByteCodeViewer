package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantDouble;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantDoubleModeler implements Modeler<ConstantDouble> {
    @Override
    public void model(final ConstantDouble element, final DocumentBuilder builder) {
        builder.add(element.getBytes());
    }
}
