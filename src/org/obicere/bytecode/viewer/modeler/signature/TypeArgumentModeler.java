package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.TypeArgument;
import org.obicere.bytecode.core.objects.signature.WildcardIndicator;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class TypeArgumentModeler implements Modeler<TypeArgument> {
    @Override
    public void model(final TypeArgument element, final DocumentBuilder builder) {
        final WildcardIndicator wildcard = element.getWildcardIndicator();

        builder.model(wildcard);
    }
}
