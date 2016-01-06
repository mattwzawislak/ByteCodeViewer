package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.BootstrapMethod;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class BootstrapMethodsAttributeModeler implements Modeler<BootstrapMethodsAttribute> {

    @Override
    public void model(final BootstrapMethodsAttribute element, final DocumentBuilder builder) {
        final BootstrapMethod[] methods = element.getBootstrapMethods();

        for (final BootstrapMethod method : methods) {
            builder.newLine();
            builder.newLine();
            builder.model(method);
        }
    }
}
