package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;

/**
 * @author Obicere
 */
public class RuntimeInvisibleTypeAnnotationsAttribute extends Attribute {

    private final TypeAnnotation[] annotations;

    public RuntimeInvisibleTypeAnnotationsAttribute(final TypeAnnotation[] annotations) {

        if (annotations == null) {
            throw new NullPointerException("annotations not defined.");
        }

        this.annotations = annotations;
    }

    public TypeAnnotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        for (final TypeAnnotation annotation : annotations) {
            //annotation.model(builder, parent);
        }
    }
}
