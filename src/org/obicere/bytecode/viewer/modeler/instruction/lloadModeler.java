package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultLLoad;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class lloadModeler extends InstructionModeler<DefaultLLoad> {
    @Override
    protected void modelValue(final DefaultLLoad element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
