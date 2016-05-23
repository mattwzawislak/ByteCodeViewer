package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.tableswitch;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class tableswitchModeler extends InstructionModeler<tableswitch> {
    @Override
    protected void modelValue(final tableswitch element, final DocumentBuilder builder) {
        final Label defaultValue = element.getDefaultOffset();
        final int low = element.getLow();
        final Label[] jumpOffsets = element.getOffsets();

        builder.add(" {");
        builder.indent();

        for (int i = 0; i < jumpOffsets.length; i++) {
            final long key = low + i;
            final Label label = jumpOffsets[i];

            builder.newLine();
            builder.add(key);
            builder.add(":");
            builder.tab();
            builder.model(label);
        }

        builder.newLine();
        builder.addKeyword("default");
        builder.add(":");
        builder.tab();
        builder.model(defaultValue);
        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
