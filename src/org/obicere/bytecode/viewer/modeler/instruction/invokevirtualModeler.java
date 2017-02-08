package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultInvokeVirtual;
import org.obicere.bytecode.core.objects.constant.AbstractConstant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokevirtualModeler extends InstructionModeler<DefaultInvokeVirtual> {
    @Override
    protected void modelValue(final DefaultInvokeVirtual element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final AbstractConstant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
