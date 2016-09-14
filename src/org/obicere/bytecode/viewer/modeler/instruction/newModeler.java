package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.constant.Constant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.code.instruction.new_;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class newModeler extends InstructionModeler<new_> {
    @Override
    protected void modelValue(final new_ element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
