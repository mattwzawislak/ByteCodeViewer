package org.obicere.bcviewer.bytecode.signature;

/**
 */
public class Result {

    private final JavaTypeSignature javaTypeSignature;

    private Result(final JavaTypeSignature javaTypeSignature) {
        this.javaTypeSignature = javaTypeSignature;
    }

    public JavaTypeSignature getJavaTypeSignature() {
        return javaTypeSignature;
    }

    public static Result parse(final QueueString string) {
        if (!string.hasNext()) {
            return null;
        }
        final JavaTypeSignature signature = JavaTypeSignature.parse(string);
        if (signature == null) {
            return null;
        }
        return new Result(signature);
    }

}
