package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.ClassTypeSignature;
import org.obicere.bytecode.core.objects.signature.SuperinterfaceSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class SuperinterfaceSignatureModeler implements Modeler<SuperinterfaceSignature> {
    @Override
    public void model(final SuperinterfaceSignature element, final DocumentBuilder builder) {
        final ClassTypeSignature signature = element.getClassTypeSignature();

        builder.model(signature);
    }
}
