package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.constant.Constant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.code.instruction.checkcast;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class checkcastModeler extends InstructionModeler<checkcast> {
    @Override
    protected void modelValue(final checkcast element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
