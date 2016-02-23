package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.instruction.invokedynamic;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokedynamicModeler extends InstructionModeler<invokedynamic> {
    @Override
    protected void modelValue(final invokedynamic element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int byte3 = element.getByte3();
        final int byte4 = element.getByte4();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
        builder.tab();
        builder.add(byte3);
        builder.tab();
        builder.add(byte4);
    }
}
