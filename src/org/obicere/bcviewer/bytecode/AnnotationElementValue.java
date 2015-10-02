package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

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

    @Override
    public void model(final DocumentBuilder builder){
        annotation.model(builder);
    }
}
