package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.ThrowsSignature;
import org.obicere.bytecode.core.objects.signature.ThrowsSignatures;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class ThrowsSignaturesModeler implements Modeler<ThrowsSignatures> {
    @Override
    public void model(final ThrowsSignatures element, final DocumentBuilder builder) {
        final ThrowsSignature[] signatures = element.getSignatures();

        boolean first = true;
        for (final ThrowsSignature signature : signatures) {
            if (!first) {
                builder.comma();
            }
            builder.model(signature);
            first = false;
        }
    }
}
