package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.newarray;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class newarrayModeler extends InstructionModeler<newarray> {
    @Override
    protected void modelValue(final newarray element, final DocumentBuilder builder) {
        final String type = element.getType();

        builder.tab();
        builder.add(type);
        builder.add("[]");
        // include square brackets to show its an array-type
    }
}
