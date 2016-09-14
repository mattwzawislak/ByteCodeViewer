package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.attribute.SourceDebugExtensionAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class SourceDebugExtensionAttributeModeler implements Modeler<SourceDebugExtensionAttribute> {
    @Override
    public void model(final SourceDebugExtensionAttribute element, final DocumentBuilder builder) {
        final String debugExtension = element.getDebugExtension();

        builder.add("Debugging File: ");
        builder.add(debugExtension);
    }
}
