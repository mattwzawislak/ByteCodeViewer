package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.signature.TypeVariableSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class TypeVariableSignatureModeler implements Modeler<TypeVariableSignature> {
    @Override
    public void model(final TypeVariableSignature element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();
        final String name = element.getName();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }
        builder.addType(name);
    }
}
