package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.fstore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class fstoreModeler extends InstructionModeler<fstore> {
    @Override
    protected void modelValue(final fstore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
