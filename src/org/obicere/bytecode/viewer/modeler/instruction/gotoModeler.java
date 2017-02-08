package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultGoto;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class gotoModeler extends InstructionModeler<DefaultGoto> {
    @Override
    protected void modelValue(final DefaultGoto element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
