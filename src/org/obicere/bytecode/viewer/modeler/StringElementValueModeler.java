package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.annotation.StringElementValue;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class StringElementValueModeler implements Modeler<StringElementValue> {
    @Override
    public void model(final StringElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final String value = constantPool.getAsString(constantValueIndex);

        builder.addString(value);
    }
}
