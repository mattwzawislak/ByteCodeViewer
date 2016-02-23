package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.instruction.ldc2_w;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class ldc2_wModeler extends InstructionModeler<ldc2_w> {
    @Override
    protected void modelValue(final ldc2_w element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
