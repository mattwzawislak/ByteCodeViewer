package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 * @author Obicere
 */
public class ConstantPool extends BytecodeElement {

    private final Constant[] constants;

    public ConstantPool(final Constant[] constants) {
        this.constants = constants;
    }

    public Constant get(final int index) {
        return constants[index];
    }

    public String getAsString(final int index) {
        final Constant constant = get(index);
        if (constant == null) {
            return "<null entry>";
        }
        return constant.toString(this);
    }

    public Constant[] getConstants() {
        return constants;
    }

    @Override
    public String getIdentifier() {
        return "constantPool";
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {

        builder.add("Constant Pool:");
        builder.indent();
        builder.newLine();

        // start at i=1 to avoid the always-null and never used constant
        for (int i = 1; i < constants.length; i++) {

            final Constant constant = constants[i];
            if (constant == null) {
                // maybe move this to a ConstantNull class with a modeler there?
                builder.addKeyword("null");
            } else {
                constant.model(builder);
            }
            builder.newLine();
        }
        builder.unindent();
    }
}
