package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.tableswitch;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class tableswitchModeler extends InstructionModeler<tableswitch> {
    @Override
    protected void modelValue(final tableswitch element, final DocumentBuilder builder) {
        final int defaultValue = element.getDefault();
        final int high = element.getHigh();
        final int low = element.getLow();
        final int[] jumpOffsets = element.getJumpOffsets();

        final long bits = ((long) high << 32) | low;

        builder.tab();
        builder.add(defaultValue);
        builder.tab();
        builder.add(bits);
        builder.tab();

        builder.add("[");
        boolean first = true;

        for(final int jump : jumpOffsets){
            if(!first){
                builder.comma();
            }
            builder.add(jump);
            first = false;
        }

        builder.add("]");
    }
}
