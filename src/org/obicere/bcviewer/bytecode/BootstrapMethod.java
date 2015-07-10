package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class BootstrapMethod {

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

}
