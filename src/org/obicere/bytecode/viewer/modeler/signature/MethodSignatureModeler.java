package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.core.objects.signature.ThrowsSignatures;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public class MethodSignatureModeler implements Modeler<MethodSignature> {
    @Override
    public void model(final MethodSignature element, final DocumentBuilder builder) {
        final ThrowsSignatures signatures = element.getThrowsSignatures();

        builder.model(element.getTypeParameters());
        builder.model(element.getResult());
        builder.pad(1);
        builder.model(element.getParameters());
        if (signatures.getSignatures().length > 0) {
            builder.addKeyword(" throws ");
            builder.model(element.getThrowsSignatures());
        }
    }
}
