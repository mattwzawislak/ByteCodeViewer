package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantPoolModeler implements Modeler<ConstantPool> {

    private static final int MAX_NAME_LENGTH = 11;

    @Override
    public void model(final ConstantPool element, final DocumentBuilder builder) {
        builder.add("Constant Pool:");
        builder.indent();
        builder.openCollapsibleBlock();
        //builder.newLine();

        final Constant[] constants = element.getConstants();
        // start at i=1 to avoid the always-null and never used constant
        for (int i = 1; i < constants.length; i++) {

            final Constant constant = constants[i];
            if (constant == null) {
                // maybe move this to a ConstantNull class with a modeler there?
                builder.addKeyword("null");
            } else {
                final String type = constant.getName();
                builder.addKeyword(type);
                builder.padTabbed(type.length(), MAX_NAME_LENGTH);
                builder.model(constant);
            }
            builder.newLine();
        }
        builder.unindent();
        builder.closeBlock();
        builder.newLine();
    }
}
