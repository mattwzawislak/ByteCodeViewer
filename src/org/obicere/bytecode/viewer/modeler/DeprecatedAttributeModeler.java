package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.attribute.DeprecatedAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class DeprecatedAttributeModeler implements Modeler<DeprecatedAttribute> {
    @Override
    public void model(final DeprecatedAttribute element, final DocumentBuilder builder) {
        builder.addAnnotation("Deprecated");
    }
}
