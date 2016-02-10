package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.dload;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class dloadModeler extends InstructionModeler<dload> {
    @Override
    protected void modelValue(final dload element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
