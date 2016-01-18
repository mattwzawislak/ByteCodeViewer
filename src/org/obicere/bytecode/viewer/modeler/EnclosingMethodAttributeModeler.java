package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.EnclosingMethodAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class EnclosingMethodAttributeModeler implements Modeler<EnclosingMethodAttribute> {
    @Override
    public void model(final EnclosingMethodAttribute element, final DocumentBuilder builder) {
        builder.addComment("Enclosed in method");
    }
}
