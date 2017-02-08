package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultILoad;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class iloadModeler extends InstructionModeler<DefaultILoad> {
    @Override
    protected void modelValue(final DefaultILoad element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        builder.tab();
        builder.add(index);
    }
}
