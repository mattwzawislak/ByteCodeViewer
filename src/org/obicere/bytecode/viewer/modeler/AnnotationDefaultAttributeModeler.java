package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class AnnotationDefaultAttributeModeler implements Modeler<AnnotationDefaultAttribute> {

    @Override
    public void model(final AnnotationDefaultAttribute element, final DocumentBuilder builder) {
        builder.addKeyword(" default ");
        builder.model(element.getDefaultValue());
    }
}
