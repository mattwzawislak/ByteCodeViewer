package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class AnnotationElementValue extends ElementValue {

    private static final int TAG = '@';

    private final Annotation annotation;

    public AnnotationElementValue(final Annotation annotation) {
        super(TAG);
        this.annotation = annotation;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
