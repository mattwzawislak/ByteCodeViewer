package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.instruction.tableswitch;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class tableswitchModeler extends InstructionModeler<tableswitch> {
    @Override
    protected void modelValue(final tableswitch element, final DocumentBuilder builder) {
        final CodeAttribute attribute = (CodeAttribute) builder.getProperty("code");

        final int defaultValue = element.getDefault();
        final int low = element.getLow();
        final int[] jumpOffsets = element.getJumpOffsets();

        builder.add(" {");
        builder.indent();

        for (int i = 0; i < jumpOffsets.length; i++) {
            final long key = low + i;
            final int offset = jumpOffsets[i];
            final String name = attribute.getBlockName(element.getStart(), offset);

            builder.newLine();
            builder.add(key);
            builder.add(":");
            builder.tab();
            if (name == null) {
                builder.add(offset);
            } else {
                builder.add(name);
            }
        }
        final String name = attribute.getBlockName(element.getStart(), defaultValue);

        builder.newLine();
        builder.addKeyword("default");
        builder.add(":");
        builder.tab();
        if (name == null) {
            builder.add(defaultValue);
        } else {
            builder.add(name);
        }
        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
