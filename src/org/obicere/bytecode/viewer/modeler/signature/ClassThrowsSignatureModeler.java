package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.ClassThrowsSignature;
import org.obicere.bytecode.core.objects.signature.ClassTypeSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class ClassThrowsSignatureModeler implements Modeler<ClassThrowsSignature> {
    @Override
    public void model(final ClassThrowsSignature element, final DocumentBuilder builder) {
        final ClassTypeSignature signature = element.getClassTypeSignature();

        builder.model(signature);
    }
}
