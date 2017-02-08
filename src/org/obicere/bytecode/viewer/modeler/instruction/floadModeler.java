package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultFLoad;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class floadModeler extends InstructionModeler<DefaultFLoad> {
    @Override
    protected void modelValue(final DefaultFLoad element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
