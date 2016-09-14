package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.signature.ClassBound;
import org.obicere.bytecode.core.objects.signature.InterfaceBound;
import org.obicere.bytecode.core.objects.signature.ReferenceTypeSignature;
import org.obicere.bytecode.core.objects.signature.TypeParameter;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class TypeParameterModeler implements Modeler<TypeParameter> {
    @Override
    public void model(final TypeParameter element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();

        final String name = element.getName();
        final ClassBound classBound = element.getClassBound();
        final InterfaceBound[] interfaceBounds = element.getInterfaceBounds();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }

        builder.addType(name);

        boolean classModeled = false;
        final ReferenceTypeSignature classReference = classBound.getReferenceTypeSignature();

        if (classReference != null) {
            // TODO, resolve if the reference is java.lang.Object
            builder.addKeyword(" extends ");
            builder.model(classReference);
            classModeled = true;
        }

        for (final InterfaceBound bound : interfaceBounds) {
            if (!classModeled) {
                builder.addKeyword(" extends ");
            } else {
                builder.add(" & ");
            }
            final ReferenceTypeSignature interfaceReference = bound.getReferenceTypeSignature();

            builder.model(interfaceReference);
        }
    }
}
