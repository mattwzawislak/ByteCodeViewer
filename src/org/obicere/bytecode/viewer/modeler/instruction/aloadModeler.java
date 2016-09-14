package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.aload;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class aloadModeler extends InstructionModeler<aload> {
    @Override
    protected void modelValue(final aload element, final DocumentBuilder builder) {
        builder.tab();
        builder.add(element.getIndex());
    }
}
