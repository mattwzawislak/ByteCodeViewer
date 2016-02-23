package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.ClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.SuperclassSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class SuperclassSignatureModeler implements Modeler<SuperclassSignature> {
    @Override
    public void model(final SuperclassSignature element, final DocumentBuilder builder) {
        final ClassTypeSignature signature = element.getClassTypeSignature();

        builder.model(signature);
    }
}
