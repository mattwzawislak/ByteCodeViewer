package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.Instruction;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class EmptyInstructionModeler extends InstructionModeler<Instruction> {

    private static final EmptyInstructionModeler INSTANCE = new EmptyInstructionModeler();

    public static InstructionModeler<? extends Instruction> getInstance(){
        return INSTANCE;
    }

    @Override
    protected void modelValue(final Instruction element, final DocumentBuilder builder) {
        // do nothing
    }
}
