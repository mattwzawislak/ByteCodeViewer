package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.constant.Constant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.code.instruction.anewarray;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class anewarrayModeler extends InstructionModeler<anewarray> {
    @Override
    protected void modelValue(final anewarray element, final DocumentBuilder builder) {
        final int index = element.getIndex();
        final ConstantPool constantPool = builder.getConstantPool();
        final Constant signature = constantPool.get(index);

        builder.tab();
        builder.model(signature);
    }
}
