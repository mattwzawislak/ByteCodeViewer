package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultGetField;
import org.obicere.bytecode.core.objects.constant.AbstractConstant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class getfieldModeler extends InstructionModeler<DefaultGetField> {
    @Override
    protected void modelValue(final DefaultGetField element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final AbstractConstant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
