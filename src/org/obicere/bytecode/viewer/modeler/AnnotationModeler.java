package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.annotation.Annotation;
import org.obicere.bytecode.core.objects.annotation.ElementValuePair;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

/**
 * @author Obicere
 */
public class AnnotationModeler implements Modeler<Annotation> {

    @Override
    public void model(final Annotation element, final DocumentBuilder builder) {
        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode", false);

        final int typeIndex = element.getTypeIndex();

        final String annotationType = builder.getConstantPool().getAsString(typeIndex);

        final String identifier;
        if (importMode) {
            identifier = ByteCodeUtils.getClassName(annotationType);
        } else {
            identifier = ByteCodeUtils.getQualifiedName(annotationType);
        }

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
