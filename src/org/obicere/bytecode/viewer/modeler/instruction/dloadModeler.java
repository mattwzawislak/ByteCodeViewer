package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultDLoad;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class dloadModeler extends InstructionModeler<DefaultDLoad> {
    @Override
    protected void modelValue(final DefaultDLoad element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
