package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.constant.ConstantFloat;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.annotation.FloatElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class FloatElementValueModeler implements Modeler<FloatElementValue> {
    @Override
    public void model(final FloatElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final ConstantFloat constant = (ConstantFloat) constantPool.get(constantValueIndex);

        builder.add(constant.getBytes());
    }
}
