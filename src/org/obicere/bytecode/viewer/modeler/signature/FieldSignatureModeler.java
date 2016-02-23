package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.core.objects.signature.JavaTypeSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class FieldSignatureModeler implements Modeler<FieldSignature> {
    @Override
    public void model(final FieldSignature element, final DocumentBuilder builder) {
        final JavaTypeSignature signature = element.getJavaTypeSignature();

        builder.model(signature);
    }
}
