package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultDStore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class dstoreModeler extends InstructionModeler<DefaultDStore> {
    @Override
    protected void modelValue(final DefaultDStore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
