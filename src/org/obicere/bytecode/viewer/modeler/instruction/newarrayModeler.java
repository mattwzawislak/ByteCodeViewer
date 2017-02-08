package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.code.instruction.DefaultNewArray;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class newarrayModeler extends InstructionModeler<DefaultNewArray> {
    @Override
    protected void modelValue(final DefaultNewArray element, final DocumentBuilder builder) {
        final String type = element.getType();

        builder.tab();
        builder.addKeyword(type);
        builder.add(".");
        builder.addKeyword("class");
    }
}
