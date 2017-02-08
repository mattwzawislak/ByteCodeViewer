package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultLookupSwitch;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class lookupswitchModeler extends InstructionModeler<DefaultLookupSwitch> {
    @Override
    protected void modelValue(final DefaultLookupSwitch element, final DocumentBuilder builder) {
        final Label defaultOffset = element.getDefaultOffset();

        final int[] matches = element.getMatches();
        final Label[] offsets = element.getOffsets();

        // we can skip the number of pairs
        // builder.tab();
        // builder.add(nPairs);
        builder.add(" {");
        builder.indent();
        for(int i = 0; i < matches.length; i++) {
            builder.newLine();

            builder.add(matches[i]);
            builder.add(":");
            builder.tab();
            builder.model(offsets[i]);
        }

        builder.newLine();
        builder.addKeyword("default");
        builder.add(":");
        builder.tab();
        builder.model(defaultOffset);

        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
