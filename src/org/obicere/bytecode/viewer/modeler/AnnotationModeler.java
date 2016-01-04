package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.Annotation;
import org.obicere.bytecode.core.objects.ElementValuePair;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 * @author Obicere
 */
public class AnnotationModeler implements Modeler<Annotation> {

    @Override
    public void model(final Annotation element, final DocumentBuilder builder) {
        final int typeIndex = element.getTypeIndex();

        final String identifier = ByteCodeUtils.getQualifiedName(builder.getConstantPool().getAsString(typeIndex));
        builder.addAnnotation(identifier);

        final ElementValuePair[] pairs = element.getElementValuePairs();
        if (pairs.length > 0) {
            builder.add("(");
            boolean first = true;
            for (final ElementValuePair pair : pairs) {
                if (!first) {
                    builder.comma();
                }
                builder.model(pair);
                first = false;
            }
            builder.add(")");
        }
    }
}
