package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.AbstractInstruction;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class EmptyInstructionModeler extends InstructionModeler<AbstractInstruction> {

    private static final EmptyInstructionModeler INSTANCE = new EmptyInstructionModeler();

    public static InstructionModeler<? extends AbstractInstruction> getInstance(){
        return INSTANCE;
    }

    @Override
    protected void modelValue(final AbstractInstruction element, final DocumentBuilder builder) {
        // do nothing
    }
}
