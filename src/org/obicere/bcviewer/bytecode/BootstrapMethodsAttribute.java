package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class BootstrapMethodsAttribute extends Attribute {

    private final BootstrapMethod[] bootstrapMethods;

    public BootstrapMethodsAttribute(final int attributeNameIndex, final int attributeLength, final BootstrapMethod[] bootstrapMethods) {
        super(attributeNameIndex, attributeLength);

        if (bootstrapMethods == null) {
            throw new NullPointerException("bootstrap methods not defined.");
        }

        this.bootstrapMethods = bootstrapMethods;
    }

    public BootstrapMethod[] getBootstrapMethods() {
        return bootstrapMethods;
    }

}
