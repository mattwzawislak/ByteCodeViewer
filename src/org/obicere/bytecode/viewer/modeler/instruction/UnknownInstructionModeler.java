package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.UnknownInstruction;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class UnknownInstructionModeler extends InstructionModeler<UnknownInstruction> {
    @Override
    protected void modelValue(final UnknownInstruction element, final DocumentBuilder builder) {
        builder.tab();
        builder.add(element.getOpcode());
    }
}
