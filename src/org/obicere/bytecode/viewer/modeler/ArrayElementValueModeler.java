package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.annotation.ArrayElementValue;
import org.obicere.bytecode.core.objects.annotation.ElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ArrayElementValueModeler implements Modeler<ArrayElementValue> {
    @Override
    public void model(final ArrayElementValue element, final DocumentBuilder builder) {
        builder.add("{");

        boolean first = true;
        for (final ElementValue value : element.getValues()) {
            if (!first) {
                builder.comma();
            }
            builder.model(value);
            first = false;
        }
        builder.add("}");
    }
}
