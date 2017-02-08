package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultInvokeInterface;
import org.obicere.bytecode.core.objects.constant.AbstractConstant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokeinterfaceModeler extends InstructionModeler<DefaultInvokeInterface> {
    @Override
    protected void modelValue(final DefaultInvokeInterface element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int count = element.getCount();
        final int byte4 = element.getByte4();

        final ConstantPool constantPool = builder.getConstantPool();
        final AbstractConstant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
        builder.tab();
        builder.add(count);
        builder.tab();
        builder.add(byte4);
    }
}
