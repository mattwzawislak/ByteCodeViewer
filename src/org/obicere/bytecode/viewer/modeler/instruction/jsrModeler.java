package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.instruction.jsr;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class jsrModeler extends InstructionModeler<jsr> {
    @Override
    protected void modelValue(final jsr element, final DocumentBuilder builder) {
        final int start = element.getStart();

        // ensure to cast to signed 16-bit §6.5.jsr
        final short offset = (short) element.getBranchOffset();

        final CodeAttribute code = (CodeAttribute) builder.getProperty("code");
        final String line = code.getBlockName(start, offset);

        builder.tab();
        if (line == null) {
            builder.add(offset);
        } else {
            builder.add(line);
        }
    }
}
