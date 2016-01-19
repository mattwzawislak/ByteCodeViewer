package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantLong;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.LongElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class LongElementValueModeler implements Modeler<LongElementValue> {
    @Override
    public void model(final LongElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final ConstantLong constant = (ConstantLong) constantPool.get(constantValueIndex);
        builder.add(constant.getBytes());
    }
}
