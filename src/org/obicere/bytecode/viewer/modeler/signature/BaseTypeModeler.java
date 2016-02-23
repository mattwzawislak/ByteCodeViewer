package org.obicere.bytecode.viewer.modeler.signature;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.signature.BaseType;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

import java.util.Set;

/**
 */
public class BaseTypeModeler implements Modeler<BaseType> {
    @Override
    public void model(final BaseType element, final DocumentBuilder builder) {
        final Set<Annotation> annotations = element.getAnnotations();
        final String type = element.getType();

        for (final Annotation annotation : annotations) {
            builder.model(annotation);
        }
        builder.addKeyword(type);
    }
}
