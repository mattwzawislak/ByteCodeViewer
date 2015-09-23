package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class ArrayTypeSignature extends ReferenceTypeSignature {

    private final JavaTypeSignature signature;

    private ArrayTypeSignature(final JavaTypeSignature signature) {
        this.signature = signature;
    }

    public JavaTypeSignature getJavaTypeSignature() {
        return signature;
    }

    public static ArrayTypeSignature parse(final QueueString string) {
        if (!string.hasNext() || string.next() != '[') {
            return null;
        }
        final JavaTypeSignature signature = JavaTypeSignature.parse(string);
        if (signature == null) {
            return null;
        }
        return new ArrayTypeSignature(signature);
    }

}
