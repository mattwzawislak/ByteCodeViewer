package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.constant.ConstantInteger;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.annotation.IntegerElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class IntegerElementValueModeler implements Modeler<IntegerElementValue> {
    @Override
    public void model(final IntegerElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final ConstantInteger constant = (ConstantInteger) constantPool.get(constantValueIndex);
        builder.add(constant.getBytes());
    }
}
