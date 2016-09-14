package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.fload;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class floadModeler extends InstructionModeler<fload> {
    @Override
    protected void modelValue(final fload element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
