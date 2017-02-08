package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultIfNonNull;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ifnonnullModeler extends InstructionModeler<DefaultIfNonNull> {
    @Override
    protected void modelValue(final DefaultIfNonNull element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
