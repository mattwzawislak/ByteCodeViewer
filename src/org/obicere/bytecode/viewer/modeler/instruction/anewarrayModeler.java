package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.instruction.anewarray;
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
