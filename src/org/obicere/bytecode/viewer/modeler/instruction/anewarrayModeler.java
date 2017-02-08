package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultANewArray;
import org.obicere.bytecode.core.objects.constant.AbstractConstant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class anewarrayModeler extends InstructionModeler<DefaultANewArray> {
    @Override
    protected void modelValue(final DefaultANewArray element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final ConstantPool constantPool = builder.getConstantPool();
        final AbstractConstant signature = constantPool.get(index);

        builder.tab();
        builder.model(signature);
    }
}
