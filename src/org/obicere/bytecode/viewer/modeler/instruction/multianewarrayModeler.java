package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultMultiANewArray;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class multianewarrayModeler extends InstructionModeler<DefaultMultiANewArray> {
    @Override
    protected void modelValue(final DefaultMultiANewArray element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int dimensions = element.getDimensions();

        builder.tab();
        builder.add(index);
        builder.tab();
        builder.add(dimensions);
    }
}
