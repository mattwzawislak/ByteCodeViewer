package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.signature.ReferenceTypeSignature;
import org.obicere.bytecode.core.objects.signature.SuperBoundedWildcardIndicator;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class SuperBoundedWildcardIndicatorModeler implements Modeler<SuperBoundedWildcardIndicator> {
    @Override
    public void model(final SuperBoundedWildcardIndicator element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();
        final ReferenceTypeSignature signature = element.getReferenceTypeSignature();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }
        builder.add("?");
        builder.addKeyword(" super ");
        builder.model(signature);
    }
}
