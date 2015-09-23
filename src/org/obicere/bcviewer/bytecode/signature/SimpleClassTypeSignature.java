package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class SimpleClassTypeSignature {

    private final String        identifier;
    private final TypeArguments typeArguments;

    public SimpleClassTypeSignature(final String identifier, final TypeArguments typeArguments) {
        this.identifier = identifier;
        this.typeArguments = typeArguments;
    }

    public String getIdentifier() {
        return identifier;
    }

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public static SimpleClassTypeSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final String identifier = string.nextIdentifier();
        if (identifier == null) {
            return null;
        }
        final TypeArguments typeArguments = TypeArguments.parse(string);
        if (typeArguments == null) {
            return null;
        }

        return new SimpleClassTypeSignature(identifier, typeArguments);
    }

}
