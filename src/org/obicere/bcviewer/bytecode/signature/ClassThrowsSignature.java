package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;

import java.util.Iterator;

/**
 */
public class ClassThrowsSignature extends ThrowsSignature {

    private final ClassTypeSignature classTypeSignature;

    private ClassThrowsSignature(final ClassTypeSignature classTypeSignature) {
        this.classTypeSignature = classTypeSignature;
    }

    public ClassTypeSignature getClassTypeSignature() {
        return classTypeSignature;
    }

    public static ThrowsSignature parse(final QueueString string) {
        final ClassTypeSignature classTypeSignature = ClassTypeSignature.parse(string);
        if (classTypeSignature == null) {
            return null;
        }
        return new ClassThrowsSignature(classTypeSignature);
    }

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        classTypeSignature.walk(annotation, path);
    }
}
