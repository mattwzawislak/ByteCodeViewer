package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeVisibleTypeAnnotationsAttribute extends Attribute {

    private final TypeAnnotation[] annotations;

    public RuntimeVisibleTypeAnnotationsAttribute(final TypeAnnotation[] annotations) {

        if (annotations == null) {
            throw new NullPointerException("annotations not defined.");
        }

        this.annotations = annotations;
    }

    public TypeAnnotation[] getAnnotations() {
        return annotations;
    }

}
