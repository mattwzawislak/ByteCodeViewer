package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.signature.ClassBound;
import org.obicere.bytecode.core.objects.signature.ReferenceTypeSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 */
public class ClassBoundModeler implements Modeler<ClassBound> {
    @Override
    public void model(final ClassBound element, final DocumentBuilder builder) {
        final ReferenceTypeSignature signature = element.getReferenceTypeSignature();
        builder.model(signature);
    }
}
