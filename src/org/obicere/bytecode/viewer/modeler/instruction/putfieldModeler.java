package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.constant.Constant;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.code.instruction.putfield;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class putfieldModeler extends InstructionModeler<putfield> {
    @Override
    protected void modelValue(final putfield element, final DocumentBuilder builder) {
        final int index = element.getIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant constant = constantPool.get(index);

        builder.tab();
        builder.model(constant);
    }
}
