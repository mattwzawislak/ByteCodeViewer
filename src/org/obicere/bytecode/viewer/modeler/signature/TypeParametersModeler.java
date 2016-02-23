package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.TypeParameter;
import org.obicere.bytecode.core.objects.signature.TypeParameters;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class TypeParametersModeler implements Modeler<TypeParameters> {
    @Override
    public void model(final TypeParameters element, final DocumentBuilder builder) {
        final TypeParameter[] types = element.getTypeParameters();
        if (types.length == 0) {
            return;
        }
        builder.add("<");

        boolean first = true;
        for (final TypeParameter type : types) {
            if (!first) {
                builder.comma();
            }
            builder.model(type);
            first = false;
        }

        builder.add("> ");
    }
}
