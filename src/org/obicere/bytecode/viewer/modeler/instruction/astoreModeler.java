package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultAStore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class astoreModeler extends InstructionModeler<DefaultAStore> {
    @Override
    protected void modelValue(final DefaultAStore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
