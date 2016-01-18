package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantUtf8;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantUtf8Modeler implements Modeler<ConstantUtf8> {
    @Override
    public void model(final ConstantUtf8 element, final DocumentBuilder builder) {
        final String bytes = element.getBytes();
        builder.addString(bytes);
    }
}
