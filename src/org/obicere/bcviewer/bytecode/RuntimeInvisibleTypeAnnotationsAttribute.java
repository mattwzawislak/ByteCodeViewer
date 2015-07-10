package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class RuntimeInvisibleTypeAnnotationsAttribute extends Attribute {

    private final TypeAnnotation[] annotations;

    public RuntimeInvisibleTypeAnnotationsAttribute(final int attributeNameIndex, final int attributeLength, final TypeAnnotation[] annotations) {
        super(attributeNameIndex, attributeLength);

        if (annotations == null) {
            throw new NullPointerException("annotations not defined.");
        }

        this.annotations = annotations;
    }

    public TypeAnnotation[] getAnnotations() {
        return annotations;
    }

}
