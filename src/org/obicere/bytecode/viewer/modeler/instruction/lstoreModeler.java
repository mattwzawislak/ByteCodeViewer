package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.lstore;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class lstoreModeler extends InstructionModeler<lstore> {
    @Override
    protected void modelValue(final lstore element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
