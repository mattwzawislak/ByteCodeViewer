package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultIf_ICmpNe;
import org.obicere.bytecode.core.objects.code.block.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class if_icmpneModeler extends InstructionModeler<DefaultIf_ICmpNe> {
    @Override
    protected void modelValue(final DefaultIf_ICmpNe element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
