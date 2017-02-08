package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultFStore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class fstoreModeler extends InstructionModeler<DefaultFStore> {
    @Override
    protected void modelValue(final DefaultFStore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
