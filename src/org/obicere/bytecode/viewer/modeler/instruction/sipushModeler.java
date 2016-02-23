package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.sipush;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class sipushModeler extends InstructionModeler<sipush> {
    @Override
    protected void modelValue(final sipush element, final DocumentBuilder builder) {
        final short value = (short) element.getValue();

        builder.tab();
        builder.add(value);
    }
}
