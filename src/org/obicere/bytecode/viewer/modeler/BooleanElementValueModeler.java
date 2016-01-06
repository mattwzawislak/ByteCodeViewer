package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.BooleanElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class BooleanElementValueModeler implements Modeler<BooleanElementValue> {
    @Override
    public void model(final BooleanElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();
        final String value = builder.getConstantPool().getAsString(constantValueIndex);
        builder.add(value.equals("true"));
    }
}
