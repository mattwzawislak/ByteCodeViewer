package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantInteger;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantIntegerModeler implements Modeler<ConstantInteger> {
    @Override
    public void model(final ConstantInteger element, final DocumentBuilder builder) {
        builder.add(element.getBytes());
    }
}
