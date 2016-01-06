package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.BooleanElementValue;
import org.obicere.bytecode.core.objects.ConstantInteger;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class BooleanElementValueModeler implements Modeler<BooleanElementValue> {
    @Override
    public void model(final BooleanElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();
        // assert by jvms 4.7.16.1 that the constant is an integer
        final ConstantInteger value = (ConstantInteger) builder.getConstantPool().get(constantValueIndex);

        // 0 == false
        // 1 == true
        builder.add(value.getBytes() != 0);
    }
}
