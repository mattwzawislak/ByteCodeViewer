package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.SimpleClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.TypeArguments;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class SimpleClassTypeSignatureModeler implements Modeler<SimpleClassTypeSignature> {
    @Override
    public void model(final SimpleClassTypeSignature element, final DocumentBuilder builder) {
        final String name = element.getName();
        final TypeArguments arguments = element.getTypeArguments();

        builder.add(name);
        builder.model(arguments);
    }
}
