package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.annotation.BooleanElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class BooleanElementValueModeler implements Modeler<BooleanElementValue> {
    @Override
    public void model(final BooleanElementValue element, final DocumentBuilder builder) {
        builder.add(element.getValue());
    }
}
