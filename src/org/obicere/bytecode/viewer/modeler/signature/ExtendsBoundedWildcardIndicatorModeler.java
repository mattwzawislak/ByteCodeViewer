package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.signature.ExtendsBoundedWildcardIndicator;
import org.obicere.bytecode.core.objects.signature.ReferenceTypeSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class ExtendsBoundedWildcardIndicatorModeler implements Modeler<ExtendsBoundedWildcardIndicator> {
    @Override
    public void model(final ExtendsBoundedWildcardIndicator element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();
        final boolean wildcardPresent = element.isWildcardPresent();
        final ReferenceTypeSignature signature = element.getReferenceTypeSignature();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }
        if (wildcardPresent) {
            builder.add("?");
            builder.addKeyword(" extends ");
        }
        builder.model(signature);
    }
}
