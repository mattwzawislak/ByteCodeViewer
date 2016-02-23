package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.wide;
import org.obicere.bytecode.core.reader.instruction.InstructionReader;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class wideModeler extends InstructionModeler<wide> {
    @Override
    protected void modelValue(final wide element, final DocumentBuilder builder) {
        final int instruction = element.getInstruction();
        final String name = element.getInstructionName();
        final int index = element.getIndex();
        final int constant = element.getConst();

        builder.tab();
        builder.addKeyword(name);
        builder.tab();
        builder.add(index);
        if (instruction == InstructionReader.OPCODE_IINC) {
            builder.tab();
            builder.add(constant);
        }
    }
}
