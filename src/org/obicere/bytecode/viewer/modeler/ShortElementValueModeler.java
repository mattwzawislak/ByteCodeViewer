package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantInteger;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.ShortElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ShortElementValueModeler implements Modeler<ShortElementValue> {
    @Override
    public void model(final ShortElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final ConstantInteger constant = (ConstantInteger) constantPool.get(constantValueIndex);
        builder.add((short) constant.getBytes());
    }
}
