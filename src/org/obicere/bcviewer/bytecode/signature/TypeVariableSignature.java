package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class TypeVariableSignature extends ReferenceTypeSignature {

    private final String identifier;

    private TypeVariableSignature(final String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static TypeVariableSignature parse(final QueueString string) {
        if (!string.hasNext() || string.next() != 'T') {
            return null;
        }
        final String identifier = string.nextIdentifier();
        if (identifier == null) {
            return null;
        }
        if (!string.hasNext() || string.next() != ';') {
            return null;
        }
        return new TypeVariableSignature(identifier);
    }
}
