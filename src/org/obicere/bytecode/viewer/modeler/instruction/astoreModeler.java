package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.astore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class astoreModeler extends InstructionModeler<astore> {
    @Override
    protected void modelValue(final astore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
