package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.goto_w;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class goto_wModeler extends InstructionModeler<goto_w> {
    @Override
    protected void modelValue(final goto_w element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
