package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import java.util.Iterator;

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

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        typeVariableSignature.walk(annotation, path);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        typeVariableSignature.model(builder);
    }
}
