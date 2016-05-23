package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.ifle;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ifleModeler extends InstructionModeler<ifle> {
    @Override
    protected void modelValue(final ifle element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
