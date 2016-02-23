package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.instruction.ldc;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class ldcModeler extends InstructionModeler<ldc> {
    @Override
    protected void modelValue(final ldc element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
