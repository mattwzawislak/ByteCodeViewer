package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.code.block.CodeBlock;
import org.obicere.bytecode.core.objects.code.instruction.AbstractInstruction;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public abstract class CodeBlockModeler<T extends CodeBlock> implements Modeler<T> {

    protected void modelInstructions(final AbstractInstruction[] instructions, final DocumentBuilder builder) {

        for (int i = 0; i < instructions.length; i++) {
            if(i != 0) {
                builder.newLine();
            }
            builder.model(instructions[i]);
        }
    }
}
