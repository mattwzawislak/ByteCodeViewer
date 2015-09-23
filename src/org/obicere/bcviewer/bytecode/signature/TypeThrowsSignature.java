package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class TypeThrowsSignature extends ThrowsSignature {

    private final TypeVariableSignature typeVariableSignature;

    private TypeThrowsSignature(final TypeVariableSignature typeVariableSignature) {
        this.typeVariableSignature = typeVariableSignature;
    }

    public TypeVariableSignature getTypeVariableSignature() {
        return typeVariableSignature;
    }

    public static ThrowsSignature parse(final QueueString string) {
        final TypeVariableSignature typeVariableSignature = TypeVariableSignature.parse(string);
        if (typeVariableSignature == null) {
            return null;
        }
        return new TypeThrowsSignature(typeVariableSignature);
    }
}
