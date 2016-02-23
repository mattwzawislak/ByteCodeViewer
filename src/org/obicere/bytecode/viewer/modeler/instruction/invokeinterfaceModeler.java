package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.instruction.invokeinterface;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokeinterfaceModeler extends InstructionModeler<invokeinterface> {
    @Override
    protected void modelValue(final invokeinterface element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final int count = element.getCount();
        final int byte4 = element.getByte4();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
        builder.tab();
        builder.add(count);
        builder.tab();
        builder.add(byte4);
    }
}
