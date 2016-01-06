package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.CharacterElementValue;
import org.obicere.bytecode.core.objects.ConstantInteger;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class CharacterElementValueModeler implements Modeler<CharacterElementValue> {
    @Override
    public void model(final CharacterElementValue element, final DocumentBuilder builder) {
        final int constantValueIndex = element.getConstantValueIndex();
        // assert by jvms 4.7.16.1 that the constant is an integer
        final ConstantInteger constant = (ConstantInteger) builder.getConstantPool().get(constantValueIndex);
        builder.add((char) constant.getBytes());
    }
}
