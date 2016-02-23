package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.instruction.Instruction;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public abstract class InstructionModeler<T extends Instruction> implements Modeler<T> {

    private static final int MAX_NAME_LENGTH = 14;

    @Override
    public void model(final T element, final DocumentBuilder builder) {
        final String mnemonic = element.getMnemonic();
        builder.addKeyword(mnemonic);
        builder.padTabbed(mnemonic.length(), MAX_NAME_LENGTH);
    }

    protected abstract void modelValue(final T element, final DocumentBuilder builder);
}
