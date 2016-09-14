package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.attribute.SyntheticAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class SyntheticAttributeModeler implements Modeler<SyntheticAttribute> {
    @Override
    public void model(final SyntheticAttribute element, final DocumentBuilder builder) {
        builder.addComment("Synthetic");
    }
}
