package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.lookupswitch;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class lookupswitchModeler extends InstructionModeler<lookupswitch> {
    @Override
    protected void modelValue(final lookupswitch element, final DocumentBuilder builder) {
        final int defaultValue = element.getDefault();
        final int nPairs = element.getNpairs();

        final int[][] matchOffsetPairs = element.getMatchOffsetPairs();

        builder.tab();
        builder.add(defaultValue);
        builder.tab();
        builder.add(nPairs);
        builder.tab();

        boolean first = true;
        builder.add("[");

        for(final int[] pair : matchOffsetPairs){
            if(!first){
                builder.comma();
            }
            builder.add(pair[0]);
            builder.add("->");
            builder.add(pair[1]);
            first = false;
        }

        builder.add("]");
    }
}
