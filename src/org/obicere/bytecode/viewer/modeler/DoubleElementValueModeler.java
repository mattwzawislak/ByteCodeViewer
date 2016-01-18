package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantDouble;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.DoubleElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class DoubleElementValueModeler implements Modeler<DoubleElementValue> {
    @Override
    public void model(final DoubleElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final ConstantDouble constant = (ConstantDouble) constantPool.get(constantValueIndex);

        builder.add(constant.getBytes());
    }
}
