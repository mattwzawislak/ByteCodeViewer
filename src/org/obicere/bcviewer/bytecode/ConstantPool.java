package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final Element branch = builder.addBranch(parent);

        builder.addPlain("Constant Pool:");
        builder.indent();
        builder.newLine();

        // start at i=1 to avoid the always-null and never used constant
        for (int i = 1; i < constants.length; i++) {
            final Element constantBranch = builder.addBranch(branch);

            final Constant constant = constants[i];
            if (constant == null) {
                // maybe move this to a ConstantNull class with a modeler there?
                builder.addKeyword("null");
            } else {
                constant.model(builder, constantBranch);
            }
            builder.newLine();
        }
        builder.unindent();
    }
}
