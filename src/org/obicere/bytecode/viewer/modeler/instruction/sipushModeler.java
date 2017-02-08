package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultSIPush;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class sipushModeler extends InstructionModeler<DefaultSIPush> {
    @Override
    protected void modelValue(final DefaultSIPush element, final DocumentBuilder builder) {
        final short value = (short) element.getValue();

        builder.tab();
        builder.add(value);
    }
}
