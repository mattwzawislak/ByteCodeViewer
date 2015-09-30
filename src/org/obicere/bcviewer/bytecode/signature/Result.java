package org.obicere.bcviewer.bytecode.signature;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypeAnnotation;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import java.util.Iterator;

/**
 */
public class Result extends AnnotationTarget {

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

    @Override
    public void walk(final TypeAnnotation annotation, final Iterator<Path> path) {
        javaTypeSignature.walk(annotation, path);
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        javaTypeSignature.model(builder);
    }
}
