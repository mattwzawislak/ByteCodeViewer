package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.multianewarray;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class multianewarrayModeler extends InstructionModeler<multianewarray> {
    @Override
    protected void modelValue(final multianewarray element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int dimensions = element.getDimensions();

        builder.newLine();
        builder.tab();
        builder.add(index);
        builder.tab();
        builder.add(dimensions);
    }
}
