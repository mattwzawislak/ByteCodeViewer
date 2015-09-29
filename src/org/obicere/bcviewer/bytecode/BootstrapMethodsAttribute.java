package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class BootstrapMethodsAttribute extends Attribute {

    private final BootstrapMethod[] bootstrapMethods;

    public BootstrapMethodsAttribute(final BootstrapMethod[] bootstrapMethods) {

        if (bootstrapMethods == null) {
            throw new NullPointerException("bootstrap methods not defined.");
        }

        this.bootstrapMethods = bootstrapMethods;
    }

    public BootstrapMethod[] getBootstrapMethods() {
        return bootstrapMethods;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {

        final BootstrapMethod[] methods = bootstrapMethods;
        if (methods.length == 0) {
            return;
        }
        final Element branch = builder.addBranch(parent);
        builder.addPlain(branch, "Bootstrap Methods:");
        builder.indent();

        for (final BootstrapMethod method : methods) {
            // add two new lines to provide better spacing
            builder.newLine(branch);
            builder.newLine(branch);
            method.model(builder, branch);
        }
        builder.unindent();
    }
}
