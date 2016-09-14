package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.annotation.Annotation;
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
        final boolean extendsObject = builder.getDomain().getSettingsController().getSettings().getBoolean("code.extendsObject", true);

        final Set<Annotation> annotations = element.getAnnotations();
        final boolean wildcardPresent = element.isWildcardPresent();
        final ReferenceTypeSignature signature = element.getReferenceTypeSignature();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }
        if (wildcardPresent) {
            builder.add("?");
        }
        // if we model java.lang.Object anyway
        // or we don't model java.lang.Object and this is not that class
        // or this is java.lang.Object, but we need to include something
        if (extendsObject || !signature.toString().equals("java.lang.Object") || !wildcardPresent) {
            if (wildcardPresent) {
                builder.addKeyword(" extends ");
            }
            builder.model(signature);
        }
    }
}
