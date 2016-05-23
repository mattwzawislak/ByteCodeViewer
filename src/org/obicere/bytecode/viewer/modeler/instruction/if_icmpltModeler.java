package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.if_icmplt;
import org.obicere.bytecode.core.objects.label.Label;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class if_icmpltModeler extends InstructionModeler<if_icmplt> {
    @Override
    protected void modelValue(final if_icmplt element, final DocumentBuilder builder) {
        final Label label = element.getLabel();

        builder.tab();
        builder.model(label);
    }
}
