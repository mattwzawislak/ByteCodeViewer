package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.TypeThrowsSignature;
import org.obicere.bytecode.core.objects.signature.TypeVariableSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class TypeThrowsSignatureModeler implements Modeler<TypeThrowsSignature> {
    @Override
    public void model(final TypeThrowsSignature element, final DocumentBuilder builder) {
        final TypeVariableSignature signature = element.getTypeVariableSignature();

        builder.model(signature);
    }
}
