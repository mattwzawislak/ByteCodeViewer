package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultIf_ICmpGe;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class if_icmpgeModeler extends InstructionModeler<DefaultIf_ICmpGe> {
    @Override
    protected void modelValue(final DefaultIf_ICmpGe element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
