package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.InterfaceBound;
import org.obicere.bytecode.core.objects.signature.ReferenceTypeSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class InterfaceBoundModeler implements Modeler<InterfaceBound> {
    @Override
    public void model(final InterfaceBound element, final DocumentBuilder builder) {
        final ReferenceTypeSignature signature = element.getReferenceTypeSignature();
        builder.model(signature);
    }
}
