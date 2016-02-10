package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.dstore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class dstoreModeler extends InstructionModeler<dstore> {
    @Override
    protected void modelValue(final dstore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
