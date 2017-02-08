package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultBIPush;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class bipushModeler extends InstructionModeler<DefaultBIPush> {
    @Override
    protected void modelValue(final DefaultBIPush element, final DocumentBuilder builder) {
        final byte value = element.getByte();

        builder.tab();
        builder.add(value);
    }
}
