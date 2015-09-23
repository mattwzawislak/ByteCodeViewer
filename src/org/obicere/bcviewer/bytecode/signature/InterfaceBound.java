package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class InterfaceBound {

    private final ReferenceTypeSignature referenceTypeSignature;

    private InterfaceBound(final ReferenceTypeSignature referenceTypeSignature) {
        this.referenceTypeSignature = referenceTypeSignature;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public static InterfaceBound parse(final QueueString string) {
        if (!string.hasNext() || string.next() != ':') {
            return null;
        }
        final ReferenceTypeSignature referenceTypeSignature = ReferenceTypeSignature.parse(string);
        if (referenceTypeSignature == null) {
            return null;
        }
        return new InterfaceBound(referenceTypeSignature);
    }

}
