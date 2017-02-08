package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultInvokeDynamic;
import org.obicere.bytecode.core.objects.constant.AbstractConstant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokedynamicModeler extends InstructionModeler<DefaultInvokeDynamic> {
    @Override
    protected void modelValue(final DefaultInvokeDynamic element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int byte3 = element.getByte3();
        final int byte4 = element.getByte4();

        final ConstantPool constantPool = builder.getConstantPool();
        final AbstractConstant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
        builder.tab();
        builder.add(byte3);
        builder.tab();
        builder.add(byte4);
    }
}
