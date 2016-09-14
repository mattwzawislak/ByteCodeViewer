package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.annotation.AnnotationElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class AnnotationElementValueModeler implements Modeler<AnnotationElementValue> {

    @Override
    public void model(final AnnotationElementValue element, final DocumentBuilder builder) {
        builder.model(element.getAnnotation());
    }
}
