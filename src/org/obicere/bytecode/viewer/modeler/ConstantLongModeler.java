package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantLong;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantLongModeler implements Modeler<ConstantLong> {
    @Override
    public void model(final ConstantLong element, final DocumentBuilder builder) {
        builder.add(element.getBytes());
    }
}
