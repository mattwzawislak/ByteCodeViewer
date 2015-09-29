package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class BootstrapMethod extends BytecodeElement {

    private final int   bootstrapMethodRef;
    private final int[] bootstrapArguments;

    public BootstrapMethod(final int bootstrapMethodRef, final int[] bootstrapArguments) {

        if (bootstrapArguments == null) {
            throw new NullPointerException("bootstrap arguments not defined.");
        }

        this.bootstrapMethodRef = bootstrapMethodRef;
        this.bootstrapArguments = bootstrapArguments;
    }

    public int getBootstrapMethodRef() {
        return bootstrapMethodRef;
    }

    public int[] getBootstrapArguments() {
        return bootstrapArguments;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String bootstrap = constantPool.getAsString(bootstrapMethodRef);
        builder.addPlain(parent, bootstrap);

        builder.indent();
        for (final int argument : bootstrapArguments) {
            final Constant constant = constantPool.get(argument);
            builder.newLine(parent);
            constant.model(builder, parent);
        }
        builder.unindent();
    }

}
