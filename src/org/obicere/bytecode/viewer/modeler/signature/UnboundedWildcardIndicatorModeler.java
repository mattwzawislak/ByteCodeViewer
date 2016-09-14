package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.signature.UnboundedWildcardIndicator;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class UnboundedWildcardIndicatorModeler implements Modeler<UnboundedWildcardIndicator> {
    @Override
    public void model(final UnboundedWildcardIndicator element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }
        builder.add("?");
    }
}
