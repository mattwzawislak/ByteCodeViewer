package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.ifeq;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ifeqModeler extends InstructionModeler<ifeq> {
    @Override
    protected void modelValue(final ifeq element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
