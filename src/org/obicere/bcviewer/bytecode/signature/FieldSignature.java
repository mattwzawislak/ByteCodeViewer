package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class FieldSignature {

    private final ReferenceTypeSignature referenceTypeSignature;

    private FieldSignature(final ReferenceTypeSignature referenceTypeSignature) {
        this.referenceTypeSignature = referenceTypeSignature;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public static FieldSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final ReferenceTypeSignature referenceTypeSignature = ReferenceTypeSignature.parse(string);

        if (referenceTypeSignature == null) {
            return null;
        }
        return new FieldSignature(referenceTypeSignature);
    }
}
