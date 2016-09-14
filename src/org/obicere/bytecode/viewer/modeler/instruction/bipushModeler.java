package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.bipush;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class bipushModeler extends InstructionModeler<bipush> {
    @Override
    protected void modelValue(final bipush element, final DocumentBuilder builder) {
        final byte value = element.getByte();

        builder.tab();
        builder.add(value);
    }
}
