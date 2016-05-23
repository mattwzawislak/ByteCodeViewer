package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.if_icmpne;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class if_icmpneModeler extends InstructionModeler<if_icmpne> {
    @Override
    protected void modelValue(final if_icmpne element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
