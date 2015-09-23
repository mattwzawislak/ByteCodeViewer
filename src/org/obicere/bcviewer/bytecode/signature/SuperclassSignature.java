package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class SuperclassSignature {

    private final ClassTypeSignature classTypeSignature;

    private SuperclassSignature(final ClassTypeSignature classTypeSignature) {
        this.classTypeSignature = classTypeSignature;
    }

    public ClassTypeSignature getClassTypeSignature() {
        return classTypeSignature;
    }

    public static SuperclassSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final ClassTypeSignature classTypeSignature = ClassTypeSignature.parse(string);
        if (classTypeSignature == null) {
            return null;
        }
        return new SuperclassSignature(classTypeSignature);
    }

}
