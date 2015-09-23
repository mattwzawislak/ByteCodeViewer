package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class ExtendsBoundedWildcardIndicator extends WildcardIndicator {

    private final ReferenceTypeSignature referenceTypeSignature;

    public ExtendsBoundedWildcardIndicator(final ReferenceTypeSignature referenceTypeSignature) {
        this.referenceTypeSignature = referenceTypeSignature;
    }

    public ReferenceTypeSignature getReferenceTypeSignature() {
        return referenceTypeSignature;
    }

    public static SuperBoundedWildcardIndicator parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        // optional +
        if (string.peek() == '+') {
            string.next();
        }
        final ReferenceTypeSignature referenceTypeSignature = ReferenceTypeSignature.parse(string);
        if (referenceTypeSignature == null) {
            return null;
        }
        return new SuperBoundedWildcardIndicator(referenceTypeSignature);
    }

}
