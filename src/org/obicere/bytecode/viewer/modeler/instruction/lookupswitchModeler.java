package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.instruction.lookupswitch;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class lookupswitchModeler extends InstructionModeler<lookupswitch> {
    @Override
    protected void modelValue(final lookupswitch element, final DocumentBuilder builder) {
        final CodeAttribute attribute = (CodeAttribute) builder.getProperty("code");

        final int defaultValue = element.getDefault();

        final int[][] matchOffsetPairs = element.getMatchOffsetPairs();

        final String defaultName = attribute.getBlockName(element.getStart(), defaultValue);

        // we can skip the number of pairs
        // builder.tab();
        // builder.add(nPairs);
        builder.add(" {");
        builder.indent();
        for (final int[] pair : matchOffsetPairs) {
            builder.newLine();

            // TODO: this would be so sexy if we could reverse the key for hashcode and whatnot
            builder.add(pair[0]);
            builder.add(":");
            builder.tab();

            final String name = attribute.getBlockName(element.getStart(), pair[1]);

            if (name == null) {
                builder.add(pair[1]);
            } else {
                builder.add(name);
            }
        }

        builder.newLine();
        builder.addKeyword("default");
        builder.add(":");
        builder.tab();
        if (defaultName == null) {
            builder.add(defaultValue);
        } else {
            builder.add(defaultName);
        }
        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
