package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultIfEq;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ifeqModeler extends InstructionModeler<DefaultIfEq> {
    @Override
    protected void modelValue(final DefaultIfEq element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
