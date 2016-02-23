package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.TypeArgument;
import org.obicere.bytecode.core.objects.signature.TypeArguments;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class TypeArgumentsModeler implements Modeler<TypeArguments> {
    @Override
    public void model(final TypeArguments element, final DocumentBuilder builder) {
        final TypeArgument[] types = element.getTypeArguments();
        if (types.length == 0) {
            return;
        }
        builder.add("<");
        boolean first = true;
        for (final TypeArgument type : types) {
            if (!first) {
                builder.comma();
            }
            builder.model(type);
            first = false;
        }
        builder.add(">");
    }
}
