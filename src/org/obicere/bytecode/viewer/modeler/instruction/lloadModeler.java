package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.lload;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class lloadModeler extends InstructionModeler<lload> {
    @Override
    protected void modelValue(final lload element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
