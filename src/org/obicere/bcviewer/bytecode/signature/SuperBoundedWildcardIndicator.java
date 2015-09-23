package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class SuperBoundedWildcardIndicator extends WildcardIndicator {

    private final ReferenceTypeSignature referenceTypeSignature;

    public SuperBoundedWildcardIndicator(final ReferenceTypeSignature referenceTypeSignature) {
        this.referenceTypeSignature = referenceTypeSignature;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public static SuperBoundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext() && string.next() != '-') {
            return null;
        }
        final ReferenceTypeSignature referenceTypeSignature = ReferenceTypeSignature.parse(string);
        if (referenceTypeSignature == null) {
            return null;
        }
        return new SuperBoundedWildcardIndicator(referenceTypeSignature);
    }

}
