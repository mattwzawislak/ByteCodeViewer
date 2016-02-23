package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.instruction.jsr_w;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class jsr_wModeler extends InstructionModeler<jsr_w> {
    @Override
    protected void modelValue(final jsr_w element, final DocumentBuilder builder) {
        final int start = element.getStart();

        final int offset = element.getBranchOffset();

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
