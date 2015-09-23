package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class SuperinterfaceSignature {

    private final ClassTypeSignature classTypeSignature;

    private SuperinterfaceSignature(final ClassTypeSignature classTypeSignature) {
        this.classTypeSignature = classTypeSignature;
    }

    public ClassTypeSignature getClassTypeSignature() {
        return classTypeSignature;
    }

    public static SuperinterfaceSignature parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final ClassTypeSignature classTypeSignature = ClassTypeSignature.parse(string);
        if (classTypeSignature == null) {
            return null;
        }
        return new SuperinterfaceSignature(classTypeSignature);
    }
}
