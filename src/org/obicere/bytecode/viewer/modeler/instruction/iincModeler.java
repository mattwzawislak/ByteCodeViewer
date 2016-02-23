package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.iinc;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class iincModeler extends InstructionModeler<iinc> {
    @Override
    protected void modelValue(final iinc element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int constant = element.getConstant();

        builder.tab();
        builder.add(index);
        builder.tab();
        builder.add(constant);
    }
}
